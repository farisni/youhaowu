import { defineConfig, globalIgnores } from 'eslint/config'
import globals from 'globals'
import js from '@eslint/js'
import fs from 'node:fs'
import pluginVue from 'eslint-plugin-vue'
import pluginOxlint from 'eslint-plugin-oxlint'

// 👇 读取 auto-import 生成的 globals
const autoImportJson = JSON.parse(
  fs.readFileSync(new URL('./.eslintrc-auto-import.json', import.meta.url))
)

export default defineConfig([
  {
    name: 'app/files-to-lint',
    files: ['**/*.{js,mjs,jsx,vue}'],
  },

  globalIgnores(['**/dist/**', '**/dist-ssr/**', '**/coverage/**']),

  {
    languageOptions: {
      globals: {
        ...globals.browser,
        ...autoImportJson.globals
      },
    },
  },

  js.configs.recommended,
  ...pluginVue.configs['flat/essential'],
  ...pluginOxlint.configs['flat/recommended'],
])
