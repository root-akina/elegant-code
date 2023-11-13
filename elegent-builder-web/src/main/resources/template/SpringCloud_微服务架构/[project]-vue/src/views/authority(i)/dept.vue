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
          :filter-node-method="filterNode"
          :expand-on-click-node="false"
          highlight-current
          :props="defaultProps"
          @node-click="handleNodeClick"
        />
      </el-aside>
      <el-main>
        <div class="filter-container">
          <el-input
            v-model="listQuery.deptNo"
            :placeholder="$t('table.deptNo')"
            style="width: 200px;"
            class="filter-item"
            @keyup.enter.native="handleFilter"
          />
          <el-input
            v-model="listQuery.deptName"
            :placeholder="$t('table.deptName')"
            style="width: 200px;"
            class="filter-item"
            @keyup.enter.native="handleFilter"
          />
          <el-select
            v-model="listQuery.leaderId"
            :placeholder="$t('table.leaderId')"
            clearable
            style="width: 190px"
            class="filter-item"
            @change="handleFilter">
            <el-option
              v-for="item in calendarUserOptions"
              :key="item.id"
              :label="item.realName"
              :value="item.id"
            />
          </el-select>
          <el-select
            v-model="listQuery.dataState"
            :placeholder="$t('table.dataState')"
            clearable
            style="width: 90px"
            class="filter-item"
            @change="handleFilter">
            <el-option
              v-for="item in calendarDataStateOptions"
              :key="item.dataValue"
              :label="item.discriptioin"
              :value="item.dataValue"
            />
          </el-select>

          <el-button
            class="filter-item"
            style="margin-left: 10px;"
            type="primary"
            icon="el-icon-circle-plus-outline"
            @click="handleCreate"
          >
            {{ $t('table.add') }}
          </el-button>
          <el-button
            class="filter-item"
            style="margin-left: 10px;"
            type="success"
            icon="el-icon-edit"
            @click="handleUpdate"
          >
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
          style="width: 100%;"
        >
          <el-table-column type="selection" width="50px" align="center" />
          <el-table-column :label="$t('table.deptNo')" show-overflow-tooltip prop="deptNo" min-width="150px" />
          <el-table-column :label="$t('table.deptName')" show-overflow-tooltip prop="deptName" min-width="150px" />
          <el-table-column :label="$t('table.leaderId')" prop="leaderId" width="120" :formatter="formatUser"/>
          <el-table-column :label="$t('table.mobile')" prop="leaderId" width="120" :formatter="formatUserMobile"/>
          <el-table-column :label="$t('table.sortNo')" prop="sortNo" width="120px" />
          <el-table-column :label="$t('table.dataState')" prop="dataState" width="120" :formatter="formatDataState"/>
          <el-table-column :label="$t('table.createTime')" prop="createTime" width="160" />
          <el-table-column align="center" :label="$t('table.actions')" width="220px">
            <template  slot-scope="{row}">
              <el-button  type="danger" v-if="row.dataState==$global.dataStateYes"
                          @click="changeDataState(row,$global.dataStateNo)"
                          size="mini" plain>
                禁用
              </el-button>
              <el-button  type="success" v-if="row.dataState==$global.dataStateNo"
                          @click="changeDataState(row,$global.dataStateYes)"
                          size="mini" plain>
                启用
              </el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="listQuery.pageNum"
          :limit.sync="listQuery.pageSize"
          @pagination="getList"
        />

        <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" width="60%" top="10px">
          <el-form
            ref="dataForm"
            :rules="rules"
            :model="temp"
            label-position="right"
            label-width="90px"
            size="small"
            style="width: 400px; margin-left:30px;"
          >
            <el-form-item :label="$t('table.parentDeptName')" prop="parentDeptNo"
                          :rules="{ required: true, message: '选择部门', trigger: 'change' }" >
              <tree-select v-if="temp.id!=''" :disabled ="true" @input="createDeptNo(temp.parentDeptNo)" v-model="temp.parentDeptNo" :data="treeQuery.treeList"></tree-select>
              <tree-select v-if="temp.id==''" :disabled ="false" @input="createDeptNo(temp.parentDeptNo)" v-model="temp.parentDeptNo" :data="treeQuery.treeList"></tree-select>
            </el-form-item>
            <el-form-item v-show="false" prop="id">
              <el-input v-model="temp.id" />
            </el-form-item>
            <el-form-item v-show="false" :label="$t('table.deptNo')" prop="deptNo">
              <el-input  v-model="temp.deptNo" :placeholder="$t('table.deptNo')" />
            </el-form-item>
            <el-form-item :label="$t('table.deptName')" prop="deptName">
              <el-input v-model="temp.deptName" :placeholder="$t('table.deptName')" />
            </el-form-item>
            <el-form-item :label="$t('table.sortNo')" prop="sortNo">
              <el-input v-model="temp.sortNo" :placeholder="$t('table.sortNo')" />
            </el-form-item>
            <el-form-item :label="$t('table.leaderId')" prop="leaderId">
              <el-select
                v-model="temp.leaderId"
                clearable
                class="filter-item"
                :placeholder="$t('table.PleaseSelect')">
                <el-option
                  v-for="item in calendarUserOptions"
                  :key="item.id"
                  :label="item.realName"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
            <el-form-item :label="$t('table.dataState')" prop="dataState">
              <el-select
                v-model="temp.dataState"
                clearable
                class="filter-item"
                :placeholder="$t('table.PleaseSelect')"
              >
                <el-option
                  v-for="item in calendarDataStateOptions"
                  :key="item.dataValue"
                  :label="item.discriptioin"
                  :value="item.dataValue"
                />
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
import {createDeptNo, fetchList, initTree,initUser,createInfo, updateInfo } from '@/api/authority/dept'
import waves from '@/directive/waves' // waves directive
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

const calendarDataStateOptions = [
  {"dataValue":"0","discriptioin":"正常"},
  {"dataValue":"1","discriptioin":"禁用"},];

const calendarUserOptions = [];
export default {
  name: 'ComplexTable',
  components: { Pagination,
    TreeSelect: () => import('@/components/TreeSelect/index.vue') },
  directives: { waves },
  data() {
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
      tableKey: 0,
      // 定义列表
      list: null,
      total: 0,
      listLoading: true,
      // 查询条件
      listQuery: {
        pageNum: 1,
        pageSize: 10,
        deptNo: null,
        deptName: null,
        leaderId: null
      },
      treeQuery: {
        dataState: '0',
        parentDeptNo:'100000000000000',
        checkedIds: [],
        treeList: [],
      },
      calendarDataStateOptions,
      calendarUserOptions,
      showReviewer: false,
      // 表单提交字段
      temp: {
        id: '',
        parentDeptNo: '',
        parentDeptName: '',
        deptNo: null,
        deptName: null,
        sortNo: '',
        dataState: '',
        leaderId:''
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑部门',
        create: '添加部门'
      },
      rules: {
        dataState: [{ required: true, message: '必填', trigger: 'blur' }],
        deptNo: [{ required: true, message: '必填', trigger: 'blur' }],
        deptName: [{ required: true, message: '必填', trigger: 'blur' }]
      },
      downloadLoading: false
    }
  },
  created() {
    this.getList();
    this.initTree();
    this.initUser();
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
    createDeptNo(op){
      createDeptNo(op).then(response => {
        this.temp.deptNo=response.data;
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
    formatDataState(row){
      for(let x of this.calendarDataStateOptions){
        if (row.dataState === x.dataValue) {
          return x.discriptioin;
        }
      }
    },
    initUser() {
      const user={
        dataState:this.$global.dataStateYes
      }
      initUser(user).then(response => {
        this.calendarUserOptions = response.data;
      })
    },
    formatUser(row){
      for(let x of this.calendarUserOptions){
        if (row.leaderId === x.id) {
          return x.realName;
        }
      }
    },
    formatUserMobile(row){
      for(let x of this.calendarUserOptions){
        if (row.leaderId === x.id) {
          return x.mobile;
        }
      }
    },
    initTree() {
      initTree(this.treeQuery).then(response => {
        console.log('查询部门树:' + response.data);
        this.tree.data = response.data.items;
        this.tree.expandedIds = response.data.expandedIds;
        this.treeQuery.treeList= response.data.items;
      })
    },
    getList() {
      this.listLoading = true;
      fetchList(this.listQuery).then(response => {
        this.list = response.data.records;
        this.total = parseInt(response.data.total);
        console.log('查询部门信息返回:' + response.data);
        // Just to simulate the time of the request
        setTimeout(() => {
          this.listLoading = false
        }, 1 * 1000)
      })
    },
    handleNodeClick(data) {
      this.listQuery.parentDeptNo = data.id;
      this.getList()
    },
    // 重新刷新页面
    handleFilter() {
      this.listQuery.pageNum = 1;
      this.getList()
    },
    handleAll() {

    },
    // 重置表单
    resetTemp() {
      this.temp = {
        id: '',
        parentDeptNo: '',
        deptNo: '',
        deptName: '',
        sortNo: '',
        dataState: '',
        leaderId:''
      }
    },
    // 添加
    handleCreate() {
      this.resetTemp();
      this.dialogStatus = 'create';
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
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
            this.initTree()
          })
        }
      })
    },
    //修改
    handleUpdate() {
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
      this.temp = Object.assign({}, rows[0]);// copy obj
      this.dialogStatus = 'update';
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
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
            this.initTree()
          })
        }
      })
    }
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
