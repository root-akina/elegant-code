<template>
  <div class="app-container">
    <el-container>
      <el-aside width="200px" height="100%">
        <el-input
          placeholder="部门过滤"
          v-model="filterText">
        </el-input>
        <el-tree
          ref="deptTree"
          node-key="id"
          :data="tree.data"
          :default-expanded-keys="tree.expandedIds"
          :expand-on-click-node="false"
          :filter-node-method="filterNode"
          highlight-current
          :props="defaultProps"
          @node-click="handleNodeClick"/>
      </el-aside>
      <el-main>
        <div class="filter-container">
          <el-select  v-model="listQuery.roleId" clearable class="filter-item" :placeholder="$t('table.roleId')" @change="handleFilter">
            <el-option v-for="item in calendarRoles" :key="item.id" :label="item.roleName" :value="item.id">
            </el-option>
          </el-select>
          <el-input v-model="listQuery.username" :placeholder="$t('table.username')" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
          <el-input v-model="listQuery.mobile" :placeholder="$t('table.mobile')" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
          <el-select v-model="listQuery.dataState" :placeholder="$t('table.dataState')" clearable style="width: 90px" class="filter-item" @change="handleFilter">
            <el-option v-for="item in calendarDataState" :key="item.dataValue" :label="item.discriptioin" :value="item.dataValue" />
          </el-select>
          <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-circle-plus-outline" @click="handleCreate">
            {{ $t('table.add') }}
          </el-button>
          <el-button class="filter-item" style="margin-left: 10px;" type="success" icon="el-icon-edit" @click="handleUpdate">
            {{ $t('table.edit') }}
          </el-button>
        </div>

        <el-table
          ref="multipleTable"
          :key="tableKey"
          v-loading="listLoading"
          :data="list"
          stripe
          fit
          highlight-current-row
          style="width: 100%;">
          <el-table-column type="selection" align="center" width="50px" />
          <el-table-column :label="$t('table.username')" prop="username" width="160px" />
          <el-table-column :label="$t('table.realName')" prop="realName" width="110px" />
          <el-table-column :label="$t('table.mobile')" prop="mobile" width="120px" />
          <el-table-column :label="$t('table.deptName')" prop="deptPostUserVoSet" show-overflow-tooltip width="140px" :formatter="formatDept"/>
          <el-table-column :label="$t('table.email')" prop="email" width="160px" />
          <el-table-column :label="$t('table.roleId')" prop="roleVoIds" show-overflow-tooltip width="150px" :formatter="formatRole"/>
          <el-table-column :label="$t('table.dataState')" prop="dataState" width="70" :formatter="formatDataState"/>
          <el-table-column align="center" :label="$t('table.actions')" min-width="180">
            <template  slot-scope="{row}">
              <el-button  type="danger" v-if="row.dataState==$global.dataStateYes"
                          @click="changeDataState(row,$global.dataStateNo)"
                          size="mini"  plain>
                禁用
              </el-button>
              <el-button  type="success" v-if="row.dataState==$global.dataStateNo"
                          @click="changeDataState(row,$global.dataStateYes)"
                          size="mini"  plain>
                启用
              </el-button>
              <el-button  type="success"
                          @click="resetPasswords(row)"
                          size="mini" plain>
                重置密码
              </el-button>
            </template>

          </el-table-column>
        </el-table>

        <pagination v-show="total>0"
                    :total="total"
                    :page.sync="listQuery.pageNum"
                    :limit.sync="listQuery.pageSize"
                    @pagination="getList" />

        <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" width="75%" top="10px">
          <el-form  size="small" ref="dataForm" :rules="rules" :model="temp" label-position="right"  label-width="100px" style="width: 100%;">
            <el-form-item v-show="false" prop="id">
              <el-input v-model="temp.id" />
            </el-form-item>
            <el-form-item :label="$t('table.username')" prop="username" style="width: 30%">
              <el-input v-model="temp.username" />
            </el-form-item>

            <el-form-item :label="$t('table.realName')" prop="realName" style="width: 30%">
              <el-input v-model="temp.realName" />
            </el-form-item>
            <el-form-item :label="$t('table.userType')" prop="userType">
              <el-select v-model="temp.userType" clearable class="filter-item" :placeholder="$t('table.PleaseSelect')">
                <el-option v-for="item in calendarUserType" :key="item.dataValue" :label="item.discriptioin" :value="item.dataValue" />
              </el-select>
            </el-form-item>
            <el-form-item :label="$t('table.roleVoIds')" prop="roleVoIds" >
              <el-select  v-model="temp.roleVoIds" style="width: 50%" clearable class="filter-item" multiple :placeholder="$t('table.PleaseSelect')">
                <el-option v-for="item in calendarRoles" :key="item.id" :label="item.roleName" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>

            <el-form-item :label="$t('table.sex')" prop="sex">
              <el-select v-model="temp.sex" clearable class="filter-item" :placeholder="$t('table.PleaseSelect')">
                <el-option v-for="item in calendarSex" :key="item.dataValue" :label="item.discriptioin" :value="item.dataValue" />
              </el-select>
            </el-form-item>

            <el-form-item :label="$t('table.mobile')" prop="mobile" style="width: 30%">
              <el-input v-model="temp.mobile" />
            </el-form-item>

            <el-form-item :label="$t('table.email')" prop="email" style="width: 30%">
              <el-input v-model="temp.email" />
            </el-form-item>

            <el-row v-for="(postUserVo, index) in temp.deptPostUserVoSet">
              <el-col :span="5">
                <el-form-item :label="$t('table.deptName')"
                              :key="postUserVo.deptNo"
                              :prop="'deptPostUserVoSet.'+index+'.deptNo'"
                              :rules="{ required: true, message: '选择部门', trigger: 'change' }" >
                  <tree-select @input="changePosts(postUserVo.deptNo,index)"   v-model="postUserVo.deptNo" :data="treeQuery.treeList" style="width: 200px;"/>
                </el-form-item>
              </el-col>
              <el-col :span="5">
                <el-form-item :label="$t('table.postName')"
                              :key="postUserVo.postNo"
                              :prop="'deptPostUserVoSet.'+index+'.postNo'"
                              :rules="{ required: true, message: '选择职位', trigger: 'change' }" >
                  <el-select @focus="changePosts(postUserVo.deptNo,index)" v-model="postUserVo.postNo" clearable class="filter-item" :placeholder="$t('table.PleaseSelect')">
                    <el-option v-for="item in calendarPosts" :key="item.postNo" :label="item.postName" :value="item.postNo" />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-form-item :label="$t('table.dataState')" prop="dataState">
              <el-select v-model="temp.dataState" clearable class="filter-item" :placeholder="$t('table.PleaseSelect')">
                <el-option v-for="item in calendarDataState" :key="item.dataValue" :label="item.discriptioin" :value="item.dataValue" />
              </el-select>
            </el-form-item>

          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">
              {{ $t('table.cancel') }}
            </el-button>
            <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">
              {{ $t('table.confirm') }}
            </el-button>
          </div>
        </el-dialog>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import { resetPasswords,fetchList,initRoles,createInfo,updateInfo } from '@/api/authority/user'
import { initTree} from '@/api/authority/dept'
import {changePosts} from '@/api/authority/post'
import waves from '@/directive/waves' // waves directive
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import { validUsername, validEmail } from '@/utils/validate'

const calendarDepts=[];
const calendarUserType = [
  {"dataValue":"0","discriptioin":"员工"},
  {"dataValue":"1","discriptioin":"客户"},];

const calendarDataState = [
  {"dataValue":"0","discriptioin":"正常"},
  {"dataValue":"1","discriptioin":"禁用"},];

const calendarSex = [
  {"dataValue":"0","discriptioin":"男"},
  {"dataValue":"1","discriptioin":"女"},];


export default {
  name: 'ComplexTable',
  components: { Pagination,
    TreeSelect: () => import('@/components/TreeSelect/index.vue') },
  directives: { waves },
  data() {
    // 参数校验
    const validateUsername = (rule, value, callback) => {
      if (!validUsername(value)) {
        callback(new Error('请输入正确邮箱格式'))
      } else {
        callback()
      }
    };

    const validateEmail = (rule, value, callback) => {
      if (!validEmail(value)) {
        callback(new Error('请输入正确邮箱格式'))
      } else {
        callback()
      }
    };

    const validatePassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.temp.plainPassword !== '') {
          this.$refs.dataForm.validateField('password');
        }
        callback();
      }
    };
    const validateAgainPassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.temp.plainPassword) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      // 定义树形参数
      filterText: '',
      tree: {
        data: undefined,
        checkedIds: undefined,
        expandedIds: undefined
      },
      defaultProps: {
        id: 'id',
        children: 'children',
        label: 'label'
      },
      treeQuery: {
        dataState: '0',
        parentDeptNo:'100000000000000',
        treeList: [],
      },
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
      // 查询条件
      listQuery: {
        pageNum: 1,
        pageSize: 10,
        deptNo: null,
        roleVoId: null,
        roleId: null,
        mobile: null,
        dataState: null,
      },
      calendarDataState,
      calendarSex,
      calendarRoles:[],
      calendarDepts,
      calendarPosts:[],
      calendarUserType,
      showReviewer: false,
      // 表单提交字段
      temp: {
        id: '',
        username: '',
        password:'',
        plainPassword: '',
        userType:'',
        nickName:'',
        email: '',
        realName: '',
        mobile: '',
        sex: '',
        remark: '',
        openId:'',
        roleVoIds:[],
        deptPostUserVoSet:[
          {
            userId:'',
            deptNo:'',
            postNo:'',
            isLeader:'',
            isDefault:''
          }
        ],
        checkedIds:[],
        dataState: '',
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑用户',
        create: '添加用户'
      },
      rules: {
        username: [{ required: true, trigger: 'blur', validator: validateUsername }],
        plainPassword: [{ required: true,trigger: 'blur',validator: validatePassword }],
        password: [{ required: true,trigger: 'blur' ,validator: validateAgainPassword}],
        userType:[{ required: true,message: '必填', trigger: 'change' }],
        nickName: [{ required: true, message: '必填', trigger: 'change' }],
        email: [{ required: true, trigger: 'blur', validator: validateEmail }],
        realName: [{ required: true, message: '必填', trigger: 'change' }],
        mobile: [{ required: true, message: '必填', trigger: 'blur' }],
        sex: [{ required: true, message: '必填', trigger: 'change' }],
        dataState: [{ required: true, message: '必填', trigger: 'change' }],
        roleVoIds: [{ required: true, trigger: 'change',message: '请选择角色'}]
      },
      downloadLoading: false
    }
  },
  created() {
    this.initRoles();
    this.initTree();
    this.getList();
  },
  watch: {
    filterText(val) {
      this.$refs.deptTree.filter(val);
    }
  },
  methods: {
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    //初始化部门树形结构
    initTree() {
      initTree(this.treeQuery).then(response => {
        console.log('查询部门树:' + response.data);
        this.tree.data = response.data.items;
        this.tree.expandedIds = response.data.expandedIds;
        this.treeQuery.treeList= response.data.items;
      })
    },
    handleNodeClick(data) {
      this.listQuery.deptNo = data.id;
      this.getList()
    },
    removeDeptPostUserVoSet(item) {
      var index = this.temp.deptPostUserVoSet.indexOf(item)
      if (index !== -1) {
        this.temp.deptPostUserVoSet.splice(index, 1)
      }
    },
    addDeptPostUserVoSet() {
      this.calendarPosts=[];
      this.temp.deptPostUserVoSet.push({
        userId:'',
        deptNo:'',
        postNo:'',
        isLeader:'',
        isDefault:'',
        key: Date.now()
      });
    },
    //查询分页
    getList() {
      this.listLoading = true;
      fetchList(this.listQuery).then(response => {
        this.list = response.data.records;
        this.total = parseInt(response.data.total);
        console.log('查询用户信息返回:' + response.data);
        setTimeout(() => {
          this.listLoading = false
        }, 1000)
      })
    },
    resetPasswords(row){
      resetPasswords(row.id).then(response => {
        const flag = response.data;
        if (flag) {
          this.$message({
            message: '操作成功',
            type: 'success'
          });
        } else {
          this.$message({
            message: response.msg,
            type: 'warning'
          })
        }
      })
    },
    changeDataState(row,status){
      this.temp = row;
      this.temp.dataState=status;
      updateInfo(this.temp).then(response => {
        const flag = response.data;
        if (flag) {
          this.$message({
            message: '操作成功',
            type: 'success'
          });
        } else {
          this.$message({
            message: response.msg,
            type: 'warning'
          })
        }
      })
    },
    //初始化角色下拉框
    initRoles(){
      initRoles({ dataState: '0'}).then(response => {
        this.calendarRoles = response.data
      })
    },
    //初始化職位拉框
    changePosts(deptNo,index){
      const post = {
        deptNo:deptNo,
        dataState:0
      }
      changePosts(post).then(response => {
        this.calendarPosts = response.data
        this.temp.deptPostUserVoSet[index].postNo=null;
      })
    },
    formatDataState(row){
      for(let x of this.calendarDataState){
        if (row.dataState === x.dataValue) {
          return x.discriptioin;
        }
      }
    },
    formatDept(row){
      console.info("列信息："+row.deptPostUserVoSet);
      let deptName = "";
      for(let y of row.deptPostUserVoSet){
        var node = this.$refs.deptTree.getNode(y.deptNo);
        if (node!==null){
          deptName = deptName+"  "+node.label;
        }
      }
      return deptName;
    },
    formatRole(row){
      console.info("列信息："+row.roleVoIds);
      let roleName = "";
      for(let x of row.roleVoIds){
        for(let y of this.calendarRoles){
          if (x == y.id){
            roleName = roleName+"  "+y.roleName;
          }
        }
      }
      return roleName;
    },

    formatSex(row){
      for(let x of this.calendarSex){
        if (row.sex === x.dataValue) {
          return x.discriptioin;
        }
      }
    },
    formatUserType(row){
      for(let x of this.calendarUserType){
        if (row.userType === x.dataValue) {
          return x.discriptioin;
        }
      }
    },
    // 重新刷新页面
    handleFilter() {
      this.listQuery.pageNum = 1;
      this.getList()
    },
    // 重置表单
    resetTemp() {
      this.temp = {
        id: '',
        username: '',
        password:'',
        plainPassword: '',
        userType:'',
        nickName:'',
        email: '',
        realName: '',
        mobile: '',
        sex: '',
        remark: '',
        openId:'',
        roleVoIds:[],
        deptPostUserVoSet:[
          {
            userId:'',
            deptNo:'',
            postNo:'',
            isLeader:'',
            isDefault:''
          }
        ],
        checkedIds:[],
        dataState: '',
      }
    },
    // 处理添加
    handleCreate() {
      this.resetTemp();
      this.calendarPosts=[];
      this.dialogStatus = 'create';
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate();
        this.initRoles()
      })
    },
    // 执行添加
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          createInfo(this.temp).then(response => {
            this.temp = response.data;
            this.list.unshift(this.temp);
            this.dialogFormVisible = false;
            this.$message({
              message: '保存成功',
              type: 'success',
              duration: 1500,
              offset: 300
            });
          })
        }
      })
    },
    //处理修改
    handleUpdate() {
      this.resetTemp();
      const rows = this.$refs.multipleTable.selection;
      if (rows.length === 0) {
        this.$message({
          message: '请选择要编辑的记录',
          type: 'warning',
          duration: 1500,
          offset: 300
        });
        return
      }
      if (rows.length > 1) {
        this.$message({
          message: '只能选择一条记录',
          type: 'warning',
          duration: 1500,
          offset: 300
        });
        return
      }
      this.temp = Object.assign({}, rows[0]) ;// copy obj
      this.dialogStatus = 'update';
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate();
        this.initRoles();
        const post = {
          deptNo:'',
          dataState:0
        }
        changePosts(post).then(response => {
          this.calendarPosts = response.data
        })
      })
    },
    //执行修改
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          updateInfo(this.temp).then(() => {
            const index = this.list.findIndex(v => v.id === this.temp.id);
            this.list.splice(index, 1, this.temp);
            this.dialogFormVisible = false;
            this.$message({
              message: '修改成功',
              type: 'success',
              duration: 1500,
              offset: 300
            });
          })
        }
      })
    },
  }
}
</script>
<style>
.el-main {
  padding: 0px 20px;
}

.el-aside {
  background-color: #fff;
  color: #fff;
  padding: 0px;
  border: 1px solid #dfe6ec;
}
.demo-table-expand {
  font-size: 0;
}
.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
</style>
