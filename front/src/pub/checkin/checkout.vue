<template>
    <div id="checkoutAdd">
        <el-dialog :title="title" :visible.sync="visible" top="8rem" :lock-scroll="false" :show-close="false"
                   :close-on-click-modal="false">
            <el-form ref="checkoutForm" :model="item" label-width="120px">
                <el-form-item label="Tip:">
                    <p><strong>是否确认退房？</strong><br />如果实际退房时间与预期退房时间出现偏差，可适当调整房费</p>
                </el-form-item>
            </el-form>
            <span slot="footer">
      <el-button type="primary" @click="resetForm('checkoutForm')">取消</el-button>
      <el-button type="primary" @click="submitForm('checkoutForm')">提交</el-button>
    </span>
        </el-dialog>
    </div>
</template>

<script>
export default {
    props: {
        title: String,
        default: 'title'
    },
    data() {
        return {
            visible: false
        };
    },
    methods: {
        open() {
            this.visible = true;
        },
        submitForm(checkoutForm) {
            this.$confirm('确认保存吗？', '是否保存', {
                cancelButtonText: '取消',
                confirmButtonText: '确认',
                lockScroll: false,
                type: 'warning'
            }).then(() => {
                // console.log('房费调整的数据是，', this.item)
                this.$emit('confirmData');
                this.resetForm('checkoutForm');
            });
        },
        resetForm(checkoutForm) {
            this.$nextTick(() => {
                this.$refs.checkoutForm.clearValidate();
            });
            this.item = {};
            this.visible = false;
        }
    }
};
</script>

<style lang="scss">

</style>
