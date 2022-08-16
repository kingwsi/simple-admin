<template>
  <a-modal
    :title="model && model.id?'修改':'新增'"
    :width="640"
    :visible="visible"
    :confirmLoading="loading"
    @ok="() => { $emit('ok') }"
    @cancel="() => { $emit('cancel') }"
  >
    <a-spin :spinning="loading">
      <a-form-model
        ref="form"
        :model="model"
        :rules="rules"
        v-bind="formLayout">
        <a-form-model-item v-show="model && model.id" label="ID">
          <a-input :disabled="model && model.id" v-model="model.id"/>
        </a-form-model-item>
        <a-form-model-item label="总价" prop="price">
          <a-input v-model="model.price" placeholder="请输入总价" />
        </a-form-model-item>
        <a-form-model-item label="订单号" prop="tradeNo">
          <a-input v-model="model.tradeNo" placeholder="请输入订单号" />
        </a-form-model-item>
        <a-form-model-item label="订单号" prop="payTotalPrice">
          <a-input v-model="model.payTotalPrice" placeholder="请输入订单号" />
        </a-form-model-item>
        <a-form-model-item label="支付时间" prop="payTime">
          <a-input v-model="model.payTime" placeholder="请输入支付时间" />
        </a-form-model-item>
        <a-form-model-item label="" prop="goodsName">
          <a-input v-model="model.goodsName" placeholder="请输入" />
        </a-form-model-item>
        <a-form-model-item label="商品id" prop="goodsId">
          <a-input v-model="model.goodsId" placeholder="请输入商品id" />
        </a-form-model-item>
        <a-form-model-item label="类型" prop="type">
          <a-input v-model="model.type" placeholder="请输入类型" />
        </a-form-model-item>
        <a-form-model-item label="数量" prop="num">
          <a-input v-model="model.num" placeholder="请输入数量" />
        </a-form-model-item>
        <a-form-model-item label="时长" prop="timeLength">
          <a-input v-model="model.timeLength" placeholder="请输入时长" />
        </a-form-model-item>
      </a-form-model>
    </a-spin>
  </a-modal>
</template>

<script>
export default {
  props: {
    visible: {
      type: Boolean,
      required: true
    },
    loading: {
      type: Boolean,
      default: () => false
    },
    model: {
      type: Object,
      default: () => null
    }
  },
  data () {
    this.formLayout = {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 7 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 13 }
      }
    }
    return {
      form: {},
      rules: {
        price: [{ required: true, message: '请输入总价', trigger: 'change' }],
        tradeNo: [{ required: true, message: '请输入订单号', trigger: 'change' }],
        payTotalPrice: [{ required: true, message: '请输入订单号', trigger: 'change' }],
        payTime: [{ required: true, message: '请输入支付时间', trigger: 'change' }],
        goodsName: [{ required: true, message: '请输入', trigger: 'change' }],
        goodsId: [{ required: true, message: '请输入商品id', trigger: 'change' }],
        type: [{ required: true, message: '请输入类型', trigger: 'change' }],
        num: [{ required: true, message: '请输入数量', trigger: 'change' }],
        timeLength: [{ required: true, message: '请输入时长', trigger: 'change' }]
      }
    }
  },
  created () {
    console.log('custom modal created')
  }
}
</script>
