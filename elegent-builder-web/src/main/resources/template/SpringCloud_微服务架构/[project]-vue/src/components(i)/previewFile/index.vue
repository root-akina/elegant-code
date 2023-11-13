<template>
  <div :class="['dialog-box',isCollapse?'analysis-dialog':'analysis']">
    <el-dialog
      :title="`${file.title}文件预览`"
      :visible.sync="file.dialogVisible"
      :before-close="handleClose"
      width="60%"
      top="10px"
    >
      <div>
        <iframe
          class="child"
          frameborder="0"
          :src="'http://view.xdocin.com/xdoc?_xdoc=' + file.fileurl"
          :style="{ height: height }"
        />
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  props: {
    file: {
      type: Object,
      default: function() {
        return {
          fileurl: '',
          dialogVisible: false,
          title: ''
        }
      }
    }
  },
  data() {
    return {
      height: window.innerHeight*0.85 + 'px',
      dialogVisible: false
    }
  },
  // 这里是用来判断左边菜单栏是否打开
  computed: {
    ...mapGetters(['sidebar']),
    isCollapse() {
      return !this.sidebar.opened
    }
  },
  methods: {
    handleClose() {
      this.file.dialogVisible = false
    }
  }
}
</script>

<style scoped>
.child {
  width: 100%;
  height: 100%;
  border: 0;
}
/*.dialog-box>>>.el-dialog__headerbtn{*/
/*  font-size: 34px;*/
/*}*/
/*.analysis>>>.el-dialog{*/
/*  left: 119px;*/
/*}*/
/*.analysis-dialog>>>.el-dialog{*/
/*  left: 27px;*/
/*}*/
</style>
