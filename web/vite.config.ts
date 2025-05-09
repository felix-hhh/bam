import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import tsconfigPaths from "vite-tsconfig-paths";

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    tsconfigPaths(),
  ],
  css: {
    preprocessorOptions: {
      scss: {
      },
    },
  },
  build: {
    target: ["es2022"],
  },
  resolve: {
    alias: {
      "@": "/src",
      "#": "/types",
    },
  },
  server: {
    open: true,
    host: true,
    port: 3003,
  },
});
