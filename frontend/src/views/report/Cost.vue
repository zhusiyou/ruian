<template>
  <div>
    <a-form layout="inline" v-model="form">
      <a-form-item label="选择月份">
        <a-month-picker v-model:value="form.date" value-format="yyyy-MM" />
      </a-form-item>
      <a-form-item>
        <a-input placeholder="商品名称" v-model:value="form.productName" />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" @click="search"> 查询 </a-button>
      </a-form-item>
      <a-form-item>
        <a-button @click="generateCost"> 生成消耗数据 </a-button>
      </a-form-item>
      <a-form-item>
        <a-button> 导出消耗数据 </a-button>
      </a-form-item>
      <a-form-item>
        <a-button @click="resetStock"> 校正库存数据</a-button>
      </a-form-item>
    </a-form>

    <a-table
      :columns="columns"
      :data-source="data"
      :scroll="{ x: 1500, y: 300 }"
      size="small"
      bordered
    >
      <template #action>
        <a>action</a>
      </template>
    </a-table>
  </div>
</template>
<script>
import { defineComponent, reactive } from "vue";
import { cost, reset } from "@/api/stock";
import { message } from "ant-design-vue";
const columns = [
  {
    title: "商品名称",
    dataIndex: "productName",
    key: "productName",
    width: 200,
    fixed: "left",
  },
  {
    title: "本月购入",
    children: [
      {
        title: "数量",
        dataIndex: "count",
        key: "1",
      },
      {
        title: "单价",
        dataIndex: "price",
        key: "2",
      },
      {
        title: "金额",
        dataIndex: "amount",
        key: "3",
      },
    ],
  },
  {
    title: "平均",
    children: [
      {
        title: "数量",
        dataIndex: "count",
        key: "1",
      },
      {
        title: "单价",
        dataIndex: "price",
        key: "2",
      },
      {
        title: "金额",
        dataIndex: "amount",
        key: "3",
      },
    ],
  },
  {
    title: "分群消耗",
    children: [
      {
        title: "数量",
        dataIndex: "count",
        key: "1",
      },
      {
        title: "单价",
        dataIndex: "price",
        key: "2",
      },
      {
        title: "金额",
        dataIndex: "amount",
        key: "3",
      },
    ],
  },
  {
    title: "本月结存",
    children: [
      {
        title: "数量",
        dataIndex: "count",
        key: "1",
      },
      {
        title: "单价",
        dataIndex: "price",
        key: "2",
      },
      {
        title: "金额",
        dataIndex: "amount",
        key: "3",
      },
    ],
  },
  //   {
  //     title: "Action",
  //     key: "operation",
  //     fixed: "right",
  //     width: 100,
  //     slots: {
  //       customRender: "action",
  //     },
  //   },
];
const data = [];

for (let i = 0; i < 100; i++) {
  data.push({
    key: i,
    productName: `商品 ${i}`,
    address: `London Park no. ${i}`,
    count: 10,
    price: 1.2,
    amount: 12,
  });
}

const form = reactive({
  date: null,
  productName: "",
});
export default defineComponent({
  setup() {
    const search = () => {};
    const generateCost = () => {
      if (!form.date) return;
      cost(form).then((res) => {
        if (res.data) {
          message.success("操作成功");
        } else {
          message.error("操作失败");
        }
      });
    };

    const resetStock = ()=>{
      reset(form).then(res=>{
        if(res.data=='ok'){
          message.success("操作成功");
        }else{
          message.error(res.data);
        }
      })
    }
    return {
      data,
      columns,
      form,
      search,
      generateCost,
      resetStock,
    };
  },
});
</script>