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
        <a-form-model-item label="名称" prop="name">
          <a-input v-model="model.name" placeholder="请输入名称" />
        </a-form-model-item>
        <a-form-model-item label="状态" prop="status">
          <a-input v-model="model.status" placeholder="请输入状态" />
        </a-form-model-item>
        <a-form-model-item label="总价" prop="totalPrice">
          <a-input v-model="model.totalPrice" placeholder="请输入总价" />
        </a-form-model-item>
        <a-form-model-item label="外部订单号" prop="outTradeNo">
          <a-input v-model="model.outTradeNo" placeholder="请输入外部订单号" />
        </a-form-model-item>
        <a-form-model-item label="订单号" prop="tradeNo">
          <a-input v-model="model.tradeNo" placeholder="请输入订单号" />
        </a-form-model-item>
        <a-form-model-item label="总价" prop="payTotalPrice">
          <a-input v-model="model.payTotalPrice" placeholder="请输入总价" />
        </a-form-model-item>
        <a-form-model-item label="支付时间" prop="payTime">
          <a-input v-model="model.payTime" placeholder="请输入支付时间" />
        </a-form-model-item>
        <a-form-model-item label="时长" prop="timeLength">
          <a-input v-model="model.timeLength" placeholder="请输入时长" />
        </a-form-model-item>
        <a-form-model-item label="会员id" prop="memberId">
          <a-input v-model="model.memberId" placeholder="请输入会员id" />
        </a-form-model-item>
        <a-form-model-item label="会员名称" prop="memberName">
          <a-input v-model="model.memberName" placeholder="请输入会员名称" />
        </a-form-model-item>
        <a-form-model-item label="openid" prop="openid">
          <a-input v-model="model.openid" placeholder="请输入openid" />
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
        name: [{ required: true, message: '请输入名称', trigger: 'change' }],
        status: [{ required: true, message: '请输入状态', trigger: 'change' }],
        totalPrice: [{ required: true, message: '请输入总价', trigger: 'change' }],
        outTradeNo: [{ required: true, message: '请输入外部订单号', trigger: 'change' }],
        tradeNo: [{ required: true, message: '请输入订单号', trigger: 'change' }],
        payTotalPrice: [{ required: true, message: '请输入总价', trigger: 'change' }],
        payTime: [{ required: true, message: '请输入支付时间', trigger: 'change' }],
        timeLength: [{ required: true, message: '请输入时长', trigger: 'change' }],
        memberId: [{ required: true, message: '请输入会员id', trigger: 'change' }],
        memberName: [{ required: true, message: '请输入会员名称', trigger: 'change' }],
        openid: [{ required: true, message: '请输入openid', trigger: 'change' }]
      }
    }
  },
  created () {
    console.log('custom modal created')
  }
}
</script>
