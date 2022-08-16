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
        <a-form-model-item label="状态" prop="status">
          <a-input v-model="model.status" placeholder="请输入状态" />
        </a-form-model-item>
        <a-form-model-item label="描述" prop="description">
          <a-input v-model="model.description" placeholder="请输入描述" />
        </a-form-model-item>
        <a-form-model-item label="价格" prop="price">
          <a-input v-model="model.price" placeholder="请输入价格" />
        </a-form-model-item>
        <a-form-model-item label="商品名称" prop="goodsName">
          <a-input v-model="model.goodsName" placeholder="请输入商品名称" />
        </a-form-model-item>
        <a-form-model-item label="类型" prop="type">
          <a-input v-model="model.type" placeholder="请输入类型" />
        </a-form-model-item>
        <a-form-model-item label="商品封面" prop="cover">
          <a-input v-model="model.cover" placeholder="请输入商品封面" />
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
        status: [{ required: true, message: '请输入状态', trigger: 'change' }],
        description: [{ required: true, message: '请输入描述', trigger: 'change' }],
        price: [{ required: true, message: '请输入价格', trigger: 'change' }],
        goodsName: [{ required: true, message: '请输入商品名称', trigger: 'change' }],
        type: [{ required: true, message: '请输入类型', trigger: 'change' }],
        cover: [{ required: true, message: '请输入商品封面', trigger: 'change' }],
        timeLength: [{ required: true, message: '请输入时长', trigger: 'change' }]
      }
    }
  },
  created () {
    console.log('custom modal created')
  }
}
</script>
