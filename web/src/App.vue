<script setup lang="ts">
import { useRouter } from "vue-router";
const router = useRouter();
</script>

<template>
  <RouterView :key="router.currentRoute.value.fullPath" />
</template>


