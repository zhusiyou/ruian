<template>
  <a-layout id="components-layout-demo-top-side-2">
    <a-layout-header class="header">
      <div class="logo">
        <!-- <img src="/ruian.png" /> -->
      </div>
    </a-layout-header>
    <a-layout>
      <a-layout-sider width="200" style="background: #fff">
        <a-menu
          mode="inline"
          v-model:selectedKeys="selectedKeys"
          v-model:openKeys="openKeys"
          :style="{ height: '100%', borderRight: 0 }"
        >
          <a-sub-menu key="productService">
            <template v-slot:title>
            <span><!--<user-outlined />-->商品服务</span>
            </template>
            <a-menu-item key="Product" @click="link('Product')">商品管理</a-menu-item>
            <a-menu-item key="Category" @click="link('Category')">类目管理</a-menu-item>
        </a-sub-menu>
        <a-sub-menu key="customerService">
            <template v-slot:title>
            <span><!--<user-outlined />-->客户服务</span>
            </template>
            <a-menu-item key="Customer" @click="link('customer')">供应商管理</a-menu-item>
        </a-sub-menu>
        <a-sub-menu key="stockService">
          <template #title>
            <span>库存服务</span>
          </template>
          <a-menu-item key="stock" @click="link('stock')">库存管理</a-menu-item>
          <a-menu-item key="input" @click="link('input')">入库管理</a-menu-item>
          <a-menu-item key="taking" @click="link('taking')">库存盘点</a-menu-item>
        </a-sub-menu>
        <a-sub-menu key="reportService">
          <template #title><span>财务报表</span></template>
          <a-menu-item key="income" @click="link('income')">应付款管理</a-menu-item>
          <a-menu-item key="cost" @click="link('cost')">消耗管理</a-menu-item>
        </a-sub-menu>
        </a-menu>
      </a-layout-sider>
      <a-layout style="padding: 0 24px 24px">
        <a-breadcrumb style="margin: 16px 0">
          <!-- <a-breadcrumb-item>Home</a-breadcrumb-item>
          <a-breadcrumb-item>List</a-breadcrumb-item>
          <a-breadcrumb-item>App</a-breadcrumb-item> -->
        </a-breadcrumb>
        <a-layout-content
          :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
        >
          <router-view v-if="showView"></router-view>
        </a-layout-content>
      </a-layout>
    </a-layout>
  </a-layout>
</template>

<script>
// import { UserOutlined } from '@ant-design/icons-vue';
import { useRouter } from 'vue-router'
import {nextTick, ref, provide} from 'vue'
export default {
  components: {
    // UserOutlined,
  },
  setup(){
      const router = useRouter()
      function link(routeName){
          router.push({name: routeName})
      }

      const showView = ref(true);

      const reload = ()=>{
        showView.value = false;
        nextTick(()=>showView.value = true)
      }
      provide('reload', reload)

      return {
          link,
          showView,
      }
  },
  data() {
    return {
      selectedKeys: ['Product'],
      openKeys: ['productService','stockService', 'customerService', 'reportService'],
    };
  },
};
</script>

<style>
#components-layout-demo-top-side-2 .logo {
  width: 120px;
  height: 31px;
  /* background: rgba(255, 255, 255, 0.2); */
  /* margin: 16px 28px 16px 0; */
  float: left;
}
</style>