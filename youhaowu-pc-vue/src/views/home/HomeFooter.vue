<template>
  <footer class="footer">
    <div class="container">
      <div class="columns">
        <div v-for="col in columns" :key="col.title" class="col">
          <h4>{{ col.title }}</h4>
          <ul>
            <li v-for="item in col.items" :key="item">{{ item }}</li>
          </ul>
        </div>
      </div>
      <div class="copyright">© 2026 有好物 youhaowu.com 版权所有</div>
    </div>
  </footer>
</template>

<script setup>
const columns = [
  { title: '购物指南', items: ['购物流程', '会员介绍', '常见问题'] },
  { title: '配送方式', items: ['上门自提', '配送查询', '运费标准'] },
  { title: '支付方式', items: ['在线支付', '货到付款', '分期付款'] },
  { title: '售后服务', items: ['退换政策', '退款说明', '价格保护'] },
  { title: '特色服务', items: ['秒杀专区', '新品首发', '积分商城'] },
  { title: '帮助中心', items: ['联系客服', '关于我们', '加入我们'] },
]
</script>

<style lang="scss" scoped>
.footer {
  width: 100%;
  background: #2d2d2d;
  padding: 32px 0;

  .container {
    width: var(--width-content);
    margin-left: auto !important;
    margin-right: auto !important;

    .columns {
      display: grid;
      grid-template-columns: repeat(6, 1fr);
      gap: 16px;
      margin-bottom: 24px;

      .col {
        text-align: center;
        h4 { color: #fff; font-weight: bold; font-size: 14px; margin-bottom: 12px; }
        ul { list-style: none; padding: 0; margin: 0; }
        li { color: var(--color-text-light); font-size: 12px; line-height: 24px; cursor: pointer; }
        li:hover { color: #fff; }
      }
    }

    .copyright {
      text-align: center;
      font-size: 12px;
      color: var(--color-text-light);
      border-top: 1px solid #444;
      padding-top: 16px;
    }
  }
}
</style>
