<template>
  <h1>
    Contact
  </h1>

  <q-form>
    <q-input outlined v-model="firstName" label='FirstName' dense />
    <q-input outlined v-model="lastName" label='LastName' dense />
    <q-btn :onclick='addContact'>Add Contact</q-btn>
  </q-form>

  <h2>
    List of your contacts:
  </h2>
  <q-list dense bordered padding class="rounded-borders">
    <q-item  v-for='contact in contacts' :key='contact.id'>
      <q-item-section>
        {{contact.fullname}}
      </q-item-section>
    </q-item>
  </q-list>
</template>


<script setup lang='ts'>
import { Contact, ContactApi, CreateContactRequest } from 'app/gen';
import { ref, onMounted } from 'vue'


const contactApi: ContactApi = new ContactApi();

const contacts = ref(new Array<Contact>())
const firstName = ref('')
const lastName = ref('')

async function loadContacts() {
  contacts.value.splice(0, contacts.value.length, ...await contactApi.getAllContacts())
}

async function addContact() {
  const request: CreateContactRequest = {
    contact: {
      firstname: firstName.value,
      lastname: lastName.value
    }
  }
  await contactApi.createContact(request)
  await loadContacts()
}

// lifecycle hooks
onMounted(() => {
  loadContacts()
})
</script>

