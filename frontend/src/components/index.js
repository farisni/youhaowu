import CommonAside from '@/components/CommonAside.vue'
import CommonHeader from '@/components/CommonHeader.vue'
import CommonMenu from '@/components/CommonMenu.vue'
import CommonTags from '@/components/CommonTags.vue'

export const componentPlugin = {
  install(app) {
    app.component('CommonAside', CommonAside)
    app.component('CommonHeader', CommonHeader)
    app.component('CommonMenu', CommonMenu)
    app.component('CommonTags', CommonTags)
  },
}
