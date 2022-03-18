<template>
  <h1>
    Skill
  </h1>

  <q-form>
    <q-input outlined v-model="skillName" label='Skill name' dense />
    <q-select v-model="skillLevel" :options="levelOptions" label="Skill level" />
    <q-btn :onclick='addSkill'>Add Skill</q-btn>
  </q-form>

  <h2>
    List of your skills:
  </h2>
  <q-list dense bordered padding class="rounded-borders">
    <q-item clickable v-ripple v-for='skill in skills' :key='skill.id'>
      <q-item-section>
        {{skill.name + " - " +  skill.level.toString()}}
      </q-item-section>
    </q-item>
  </q-list>
</template>

<script setup lang='ts'>
import { CreateSkillRequest, Level, Skill, SkillApi } from 'app/gen';
import { ref, onMounted } from 'vue'
import { enumHelper } from 'src/helper/EnumHelper';

const skills = ref(new Array<Skill>())
const skillApi: SkillApi = new SkillApi();
const skillName = ref('')
const skillLevel = ref(Level.LOW)
const levelOptions = enumHelper.enumKeys(Level)

async function loadSkills() {
  skills.value.splice(0, skills.value.length, ...await skillApi.getAllSkill())
}

async function addSkill() {
  const request: CreateSkillRequest = {
    skill: {
      name: skillName.value,
      level: skillLevel.value
    }
  }
  await skillApi.createSkill(request)
  await loadSkills()
}

// lifecycle hooks
onMounted(() => {
  loadSkills()
})
</script>
