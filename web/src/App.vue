<script setup lang="ts">
import { watch } from "vue";
import { useRouter } from "vue-router";
const router = useRouter();
</script>

<template>
  <RouterView :key="router.currentRoute.value.fullPath" />
</template>


