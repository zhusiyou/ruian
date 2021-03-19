<template>
  <div>
    <a-form layout="inline">
      <a-form-item label="选择月份">
        <a-date-picker />
      </a-form-item>
      <a-form-item>
        <a-input placeholder="商品名称" />
      </a-form-item>
      <a-form-item>
        <a-button type="primary"> 查询 </a-button>
      </a-form-item>
      <a-form-item>
        <a-button> 生成消耗数据 </a-button>
      </a-form-item>
      <a-form-item>
        <a-button> 导出消耗数据 </a-button>
      </a-form-item>
      <a-form-item>
        <a-button> 校正库存数据??? </a-button>
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
import { defineComponent } from "vue";
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
  },{
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
  },{
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
    amount: 12
  });
}

export default defineComponent({
  data() {
    return {
      data,
      columns,
    };
  },
});
</script>