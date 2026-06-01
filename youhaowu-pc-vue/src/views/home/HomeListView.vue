<template>
  <div class="list-view">
    <div class="container">
      <div class="cate">
        <el-scrollbar height="460px">
          <div
            v-for="item in categories"
            :key="item.catId"
            class="cate-item"
            @mouseenter="onEnter(item.catId)"
            @mouseleave="onLeave"
          >
            <span class="cate-name">{{ item.name }}</span>
          </div>
        </el-scrollbar>
      </div>
      <div class="banner">
        <el-carousel height="460px">
          <el-carousel-item v-for="i in 4" :key="i">
            <div class="banner-placeholder">Banner {{ i }}</div>
          </el-carousel-item>
        </el-carousel>
      </div>
      <div class="side">
<div class="news">
          <h4>
            <span>有好物快报</span>
            <el-link type="info">更多</el-link>
          </h4>
          <ul class="news-list">
            <li v-for="item in newsList" :key="item.id">
              <span class="tag">{{ item.tag }}</span>{{ item.title }}
            </li>
          </ul>
        </div>
        <ul class="lifeservices">
          <li v-for="s in services" :key="s.name" class="life-item">
            <el-icon :size="24"><component :is="s.icon" /></el-icon>
            <span>{{ s.name }}</span>
          </li>
        </ul>
        <div class="ads">
          <img :src="sidebarBannerImg" alt="广告" />
        </div>
      </div>
      <!--  弹层：放在 .container 直属，不被 cate overflow 裁剪 -->
      <template v-for="item in categories" :key="'pop-' + item.catId">
        <div v-if="item.children?.length && activeIdx === item.catId" class="item-list" @mouseenter="onEnter(item.catId)" @mouseleave="onLeave">
          <div class="subitem">
            <dl v-for="sub in item.children" :key="sub.catId" :class="{ fore: sub === item.children[0] }">
              <dt>{{ sub.name }}</dt>
              <dd>
                <em v-for="child in sub.children" :key="child.catId">{{ child.name }}</em>
              </dd>
            </dl>
          </div>
        </div>
      </template>
    </div>
  </div>
</template>

<script setup>
import api from '@/api/index.js'
import { onMounted, ref } from 'vue'
import sidebarBannerImg from '@/assets/images/sidebar-banner.png'

const services = [
  { name: '话费', icon: 'Iphone' },
  { name: '机票', icon: 'Promotion' },
  { name: '电影票', icon: 'VideoPlay' },
  { name: '游戏', icon: 'VideoGame' },
  { name: '彩票', icon: 'Present' },
  { name: '加油站', icon: 'LocalGasStation' },
  { name: '酒店', icon: 'OfficeBuilding' },
  { name: '火车票', icon: 'Ticket' },
  { name: '众筹', icon: 'TrendCharts' },
  { name: '理财', icon: 'Money' },
  { name: '礼品卡', icon: 'Gift' },
  { name: '白条', icon: 'CreditCard' },
]
const categories = ref([])
const activeIdx = ref(0)
let leaveTimer = null
const onEnter = (id) => {
  clearTimeout(leaveTimer)
  activeIdx.value = id
}
const onLeave = () => {
  leaveTimer = setTimeout(() => { activeIdx.value = 0 }, 100)
}

const newsList = [
  { id: 1, tag: '[特惠]', title: '备战开学季 全民半价购数码' },
  { id: 2, tag: '[公告]', title: '有好物APP新版本上线' },
  { id: 3, tag: '[特惠]', title: '端午节 粽情回馈' },
  { id: 4, tag: '[公告]', title: '有好物618年中大促' },
  { id: 5, tag: '[特惠]', title: '会员日 全场8折' },
]

onMounted(async () => {
  try {
    const res = await api.getCategoryList()
    categories.value = res.data || []
  } catch {
    //  静默失败
  }
})
</script>

<style lang="scss" scoped>
.list-view {
  width: 100%;
  padding: 16px 0;

  .container {
    position: relative;
    width: var(--width-content);
    margin: 0 auto;
    display: flex;
    height: 460px;

    .cate {
      position: relative;
      width: 210px;
      flex-shrink: 0;
      height: 100%;
      
      background: var(--color-bg-page);
      /* border: 1px solid var(--color-border); */
      /* border-top: none; */
      padding: 4px 0;

      .cate-item {
        padding: 0 12px;
        height: 28px;
        line-height: 28px;
        cursor: pointer;
        &:hover {
          background: var(--color-bg-page);
          .cate-name { color: var(--color-primary); }
        }
        .cate-name {
          font-size: 13px;
          color: var(--color-text-dark);
        }
      }
    }

    .banner {
      flex: 1;
      margin: 0 12px;
      height: 100%;

      .banner-placeholder {
        width: 100%;
        height: 100%;
        background: #e0e0e0;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 18px;
        color: var(--color-text-light);
      }
    }

    .side {
      width: 250px;
      flex-shrink: 0;

      display: flex;
      flex-direction: column;
      padding: 0;

      .news {
        background: #fff;
        border: 1px solid #e4e4e4;
        border-bottom: 1px solid #e4e4e4;

        h4 {
          display: flex;
          justify-content: space-between;
          align-items: center;
          padding: 5px 8px;
          margin: 2px 3px 0;
          border-bottom: 1px solid #e4e4e4;
          line-height: 24px;
          font-size: 14px;
        }
        .news-list {
          padding: 5px 15px;
          line-height: 26px;
          font-size: 12px;
          color: var(--color-text);
          list-style: none;
          .tag { font-weight: 700; color: var(--color-primary); margin-right: 2px; }
        }
      }

      .ads {
        margin-top: 10px;
        img { width: 100%; display: block; }
      }
      .lifeservices {
        height: 196px;
        margin: 0;
        
        display: grid;
        grid-template-columns: repeat(4, 1fr);
        grid-auto-rows: 1fr;
        list-style: none;
        padding: 0;
        border-left: 1px solid #e4e4e4;
        border-right: 1px solid #e4e4e4;

        .life-item {
          box-sizing: border-box;
          display: flex;
          flex-direction: column;
          align-items: center;
          justify-content: center;
          border-right: 1px solid #e4e4e4;
          border-bottom: 1px solid #e4e4e4;
          cursor: pointer;
          color: var(--color-text);
          font-size: 12px;
          &:hover { color: var(--color-primary); }
          &:nth-child(4n) { border-right: none; }
          span { margin-top: 4px; }
        }
      }
    }
  }
}

/* 子分类弹层 — 绝对定位覆盖 banner */
.item-list {
  position: absolute;
  left: 210px;
  top: 0px;
  width: 730px;
  min-height: 460px;
  background: var(--color-bg-page);
  border: 1px solid #ddd;
  z-index: 9999;
  padding: 16px;
}
.subitem dl {
  border-top: 1px solid #eee;
  padding: 6px 0;
  overflow: hidden;
}
.subitem dl.fore { border-top: 0; }
.subitem dl dt {
  float: left;
  width: 60px;
  line-height: 22px;
  text-align: right;
  padding: 3px 6px 0 0;
  font-weight: 700;
  font-size: 13px;
}
.subitem dl dd {
  margin-left: 66px;
}
.subitem dl dd em {
  font-style: normal;
  display: inline-block;
  padding: 0 6px;
  margin: 2px 0;
  font-size: 12px;
  cursor: pointer;
  color: #666;
}
.subitem dl dd em + em::before {
  content: "|";
  margin-right: 6px;
  color: #ddd;
}
.subitem dl dd em:hover {
  color: var(--color-primary);
}
</style>
