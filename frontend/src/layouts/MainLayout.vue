<template>
  <q-layout view="lHh lpr lFf" container style="height: 100vh" class="shadow-2 rounded-borders">
    <q-header elevated>
      <q-toolbar>
        <q-toolbar-title>Contact-Api Demo</q-toolbar-title>
      </q-toolbar>

      <q-tabs align='left' >
        <q-route-tab name="contacts" label="Contacts" to='/contacts' />
        <q-route-tab name="skills" label="Skills" to='/skills'/>
      </q-tabs>
    </q-header>

    <q-page-container>
      <q-page class="q-pa-md">
        <router-view/>
      </q-page>
    </q-page-container>
  </q-layout>

</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'MainLayout',

  components: {
  }
});
</script>
