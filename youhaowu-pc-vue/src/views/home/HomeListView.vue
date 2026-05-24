<template>
  <div class="list-view">
    <div class="container">
      <div class="cate">
        <div v-for="i in 15" class="cate-item">
          <span class="cate-name">分类名称 {{ i }}</span>
        </div>
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
const services = ['话费', '机票', '酒店', '游戏', '企业购', '加油卡', '电影票', '火车票']
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
      width: 210px;
      flex-shrink: 0;
      height: 100%;
      overflow-y: auto;
      background: var(--color-bg-page);
      border: 1px solid var(--color-border);
      border-top: none;
      padding: 4px 0;

      .cate-item {
        padding: 0 12px;
        height: 28px;
        line-height: 28px;
        cursor: pointer;
        &:hover {
          background: #fff;
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
        background: #fff;
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
          background: #fff;
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
</style>
