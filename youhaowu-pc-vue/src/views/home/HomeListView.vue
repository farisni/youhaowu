<template>
  <div class="list-view">
    <div class="container">
      <div class="cate">
        <el-scrollbar height="460px">
          <div
            v-for="item in categories"
            :key="item.catId"
            class="cate-item"
            @mouseenter="activeIdx = item.catId"
            @mouseleave="activeIdx = 0"
          >
            <span class="cate-name">{{ item.name }}</span>
          </div>
        </el-scrollbar>
        <!--  弹层在 el-scrollbar 外，避免被裁剪 -->
        <template v-for="item in categories" :key="'pop-' + item.catId">
          <div v-if="item.children?.length && activeIdx === item.catId" class="item-list">
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
      <div class="banner">
        <el-carousel height="460px">
          <el-carousel-item v-for="i in 4" :key="i">
            <div class="banner-placeholder">Banner {{ i }}</div>
          </el-carousel-item>
        </el-carousel>
      </div>
      <div class="side">
        <div class="user-panel">
          <div class="user-info">
            <div class="avatar">头像</div>
            <div class="user-text">Hi, 欢迎来到有好物<br><a href="/login">登录</a> <a href="/login">注册</a></div>
          </div>
          <div class="user-links">
            <a href="#">新人福利</a>
            <a href="#">PLUS会员</a>
          </div>
        </div>
        <div class="news">
          <div class="news-tabs"><span class="active">促销</span><span>公告</span></div>
          <div class="news-list">
            <div v-for="i in 4">促销信息 {{ i }}</div>
          </div>
        </div>
        <div class="services">
          <div v-for="s in services" class="service-item">
            <div class="service-icon">IC</div>
            <span>{{ s }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import api from '@/api/index.js'
import { onMounted, ref } from 'vue'

const services = ['话费', '机票', '酒店', '游戏', '企业购', '加油卡', '电影票', '火车票']
const categories = ref([])
const activeIdx = ref(0)

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
      height: 100%;
      background: var(--color-bg-page);
      display: flex;
      flex-direction: column;
      gap: 8px;
      padding: 8px;

      .user-panel {
        background: var(--color-bg-light);
        padding: 12px;

        .user-info {
          display: flex;
          gap: 10px;
          align-items: center;
          margin-bottom: 8px;

          .avatar {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            background: #ddd;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 10px;
            color: var(--color-text-light);
          }
          .user-text {
            font-size: 12px;
            color: var(--color-text);
            a { color: var(--color-primary); }
          }
        }

        .user-links {
          display: flex;
          gap: 8px;
          a {
            flex: 1;
            text-align: center;
            background: var(--color-primary);
            color: #fff;
            font-size: 12px;
            padding: 4px 0;
            text-decoration: none;
          }
        }
      }

      .news {
        background: var(--color-bg-page);
        padding: 8px 12px;

        .news-tabs {
          display: flex;
          gap: 12px;
          margin-bottom: 6px;
          font-size: 13px;
          span {
            cursor: pointer;
            color: var(--color-text-light);
            &.active { color: var(--color-primary); font-weight: bold; }
          }
        }
        .news-list {
          font-size: 12px;
          color: var(--color-text);
          line-height: 22px;
        }
      }

      .services {
        flex: 1;
        display: grid;
        grid-template-columns: 1fr 1fr;
        gap: 4px;
        align-content: start;

        .service-item {
          display: flex;
          flex-direction: column;
          align-items: center;
          padding: 6px;
          background: var(--color-bg-page);
          font-size: 11px;
          color: var(--color-text);

          .service-icon {
            width: 24px;
            height: 24px;
            background: #ddd;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 9px;
            margin-bottom: 2px;
          }
        }
      }
    }
  }
}

/* 子分类弹层 — 绝对定位覆盖 banner */
.item-list {
  position: absolute;
  left: 210px;
  top: -1px;
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
  display: inline-block;
  height: 14px;
  line-height: 14px;
  padding: 0 8px;
  margin-top: 5px;
  border-left: 1px solid #ccc;
  font-size: 12px;
  cursor: pointer;
  color: #999;
}
.subitem dl dd em:hover {
  color: var(--color-primary);
}
</style>
