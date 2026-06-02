<template>
  <div class="rank">
    <div class="tab">
      <div class="tab-tit">
        <a v-for="t in tabs" :key="t.key" href="javascript:;" :class="{ on: active === t.key }" @click="switchTab(t.key)">
          <p class="img"><img :src="t.icon" /></p>
          <p class="text">{{ t.label }}</p>
        </a>
      </div>
    </div>
    <div class="content">
      <ul>
        <li v-if="loading">
          <div v-for="i in 4" :key="i" class="img-item">
            <p class="tab-pic">
              <el-skeleton animated><el-skeleton-item variant="image" style="width:200px;height:200px" /></el-skeleton>
            </p>
            <div class="tab-info">
              <el-skeleton animated>
                <div class="info-title"><el-skeleton-item variant="text" style="width:80%" /></div>
                <p class="info-price"><el-skeleton-item variant="text" style="width:40%" /></p>
              </el-skeleton>
            </div>
          </div>
        </li>
        <li v-else>
          <div v-for="(item, idx) in list" :key="idx" class="img-item">
            <p class="tab-pic">
              <a href="#"><img v-if="item.imgUrl" :src="item.imgUrl" /><span v-else class="no-img">暂无图片</span></a>
            </p>
            <div class="tab-info">
              <div class="info-title"><a href="#">{{ item.title }}</a></div>
              <p class="info-price">{{ item.price }}</p>
            </div>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import remai from '@/assets/icons/remai.svg'
import tejia from '@/assets/icons/tejia.svg'
import xinpin from '@/assets/icons/xinpin.svg'

const active = ref('hot')
const loading = ref(true)
const list = ref([])

const tabs = [
  { key: 'hot', label: '热卖排行', icon: remai },
  { key: 'sale', label: '特价排行', icon: tejia },
  { key: 'new', label: '新品排行', icon: xinpin },
]

const fetchList = async () => {
  loading.value = true
  try {
    const res = await fetch(`/api/cms/rank/list?tab=${active.value}`)
    const data = await res.json()
    if (data.code === 0) list.value = data.data
  } catch (e) { /* ignore */ }
  finally { loading.value = false }
}

const switchTab = (key) => {
  active.value = key
  fetchList()
}

onMounted(fetchList)
</script>

<style lang="scss" scoped>
.rank {
  width: var(--width-content);
  margin: 0 auto;
  background: #fff;
  margin-top: 10px;
  margin-bottom: 10px;

  .tab {
    margin: 0 auto;
    overflow: hidden;
    width: 312px;
    padding-top: 10px;

    .tab-tit {
      text-align: center;
      display: flex;

      a {
        display: block;
        padding: 0 18px;
        text-decoration: none;
        font-size: 16px;
        color: #999;
        flex: 1;

        p { margin: 5px 0; }

        .img img {
          width: 35px; height: 35px;
          opacity: 0.5;
        }

        .text { line-height: 28px; }
      }

      .on {
        color: #e60012;
        .img img { opacity: 1; }
      }
    }
  }

  .content {
    overflow: hidden;
    padding: 10px;

    ul {
      list-style: none; padding: 0; margin: 0;

      li {
        overflow: hidden; line-height: 18px;

        .img-item {
          border: 1px solid #e1251b;
          width: 269px;
          float: left;
          overflow: hidden;
          margin: 0 12px 10px;
          background: #fff;

          .tab-pic {
            width: 230px; height: 210px;
            overflow: hidden; text-align: center;
            margin: 5px auto 18px;

            a img { width: 200px; height: 200px; }
          }

          .tab-info {
            background: #fafafa;

            .info-title {
              height: 50px; line-height: 28px;
              overflow: hidden; margin: 0 auto; padding-left: 10px;
              a { color: #333; text-decoration: none; }
            }

            .info-price {
              font-size: 20px; color: #e1251b;
              height: 35px; padding-left: 10px; display: block;
            }
          }
        }
      }
    }
  }
}

.no-img { display: flex; align-items: center; justify-content: center; width: 200px; height: 200px; color: #ccc; font-size: 14px; }
</style>
