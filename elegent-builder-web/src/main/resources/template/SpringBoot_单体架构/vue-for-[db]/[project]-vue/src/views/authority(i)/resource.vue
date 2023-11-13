<template>
  <div class="app-container">
    <el-container>
      <el-aside width="200px" height="100%">
        <el-input
          placeholder="资源过滤"
          v-model="filterText">
        </el-input>
          <el-tree
            ref="resourceTree"
            node-key="id"
            :data="tree.data"
            :default-expanded-keys="tree.expandedIds"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            :props="defaultProps"
            @node-click="handleNodeClick"
          />
      </el-aside>
      <el-main>
        <div class="filter-container">

          <el-input
            v-model="listQuery.requestPath"
            :placeholder="$t('table.requestPath')"
            style="width: 200px;"
            class="filter-item"
            @keyup.enter.native="handleFilter"
          />
          <el-input
            v-model="listQuery.label"
            :placeholder="$t('table.label')"
            style="width: 200px;"
            class="filter-item"
            @keyup.enter.native="handleFilter"
          />
          <el-select
            v-model="listQuery.resourceType"
            :placeholder="$t('table.resourceType')"
            clearable
            style="width: 90px"
            class="filter-item"
            @change="handleFilter"
          >
            <el-option
              v-for="item in calendarResourceTypeOptions"
              :key="item.dataValue"
              :label="item.discriptioin"
              :value="item.dataValue"
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
          <el-table-column :label="$t('table.resourceName')" prop="resourceName" min-width="110px" />
          <el-table-column :label="$t('table.requestPath')" show-overflow-tooltip prop="requestPath" min-width="220px" />
          <el-table-column :label="$t('table.label')" show-overflow-tooltip prop="label" min-width="220px" />
          <el-table-column :label="$t('table.resourceType')" prop="resourceType" width="90px" :formatter="formatResourceType"/>
          <el-table-column :label="$t('table.sortNo')" prop="sortNo" width="50px" />
          <el-table-column :label="$t('table.dataState')" prop="dataState" width="100" :formatter="formatDataState"/>
          <el-table-column :label="$t('table.createTime')" prop="createTime" width="150px" />
          <el-table-column align="center" :label="$t('table.actions')" width="120">
            <template  slot-scope="{row}">
              <el-button  type="danger" v-if="row.dataState==$global.dataStateYes"
                          @click="changeDataState(row,$global.dataStateNo)"
                          size="mini" icon="el-icon-edit" plain>
                禁用
              </el-button>
              <el-button  type="success" v-if="row.dataState==$global.dataStateNo"
                          @click="changeDataState(row,$global.dataStateYes)"
                          size="mini" icon="el-icon-edit" plain>
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
            label-width="110px"
            size="small"
            style="width: 400px; margin-left:30px;"
          >
            <el-form-item :label="$t('table.parentResourceName')" prop="parentResourceNo"
                          :rules="{ required: true, message: '选择资源', trigger: 'change' }" >
              <tree-select v-if="temp.id!=''"   :disabled ="true" @input="createResourceNo(temp.parentResourceNo)" v-model="temp.parentResourceNo" :data="treeQuery.treeList"></tree-select>
              <tree-select v-if="temp.id==''" :disabled ="false" @input="createResourceNo(temp.parentResourceNo)" v-model="temp.parentResourceNo" :data="treeQuery.treeList"></tree-select>
            </el-form-item>
            <el-form-item v-show="false" prop="id">
              <el-input v-model="temp.id" />
            </el-form-item>
            <el-form-item :label="$t('table.resourceName')" prop="resourceName">
              <el-input v-model="temp.resourceName" :placeholder="$t('table.resourceName')" />
            </el-form-item>
            <el-form-item :label="$t('table.requestPath')" prop="requestPath">
              <el-input v-model="temp.requestPath" :placeholder="$t('table.requestPath')" />
            </el-form-item>
            <el-form-item :label="$t('table.label')" prop="label">
              <el-input v-model="temp.label" :placeholder="$t('table.label')" />
            </el-form-item>
            <el-form-item :label="$t('table.icon')" prop="icon">
              <el-input v-model="temp.icon" :placeholder="$t('table.icon')" />
            </el-form-item>
            <el-form-item :label="$t('table.sortNo')" prop="sortNo">
              <el-input v-model="temp.sortNo" :placeholder="$t('table.sortNo')" />
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
            <el-form-item :label="$t('table.remark')" prop="remark">
              <el-input v-model="temp.remark" :placeholder="$t('table.remark')" />
            </el-form-item>
            <el-form-item :label="$t('table.resourceType')" prop="resourceType">
              <el-select v-model="temp.resourceType" clearable class="filter-item" :placeholder="$t('table.PleaseSelect')">
                <el-option
                  v-for="item in calendarResourceTypeOptions"
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
import { createResourceNo,fetchList, initTree, createInfo, updateInfo } from '@/api/authority/resource'
import waves from '@/directive/waves' // waves directive
import Pagination from '@/components/Pagination'
import { createDeptNo } from '@/api/authority/dept' // secondary package based on el-pagination

const calendarDataStateOptions = [
  {"dataValue":"0","discriptioin":"正常"},
  {"dataValue":"1","discriptioin":"禁用"},];

const calendarResourceTypeOptions = [
  {"dataValue":"s","discriptioin":"平台"},
  {"dataValue":"c","discriptioin":"目录"},
  {"dataValue":"m","discriptioin":"菜单"},
  {"dataValue":"r","discriptioin":"按钮"},
];

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
        resourceName: null,
        label: null,
        requestPath: null,
        resourceType: null,
        dataState: null,
        parentResourceNo: null,
        parentResourceName: null
      },
      treeQuery: {
        dataState: '0',
        parentResourceNo:'100000000000000',
        checkedIds: [],
        treeList:[]
      },
      calendarDataStateOptions,
      calendarResourceTypeOptions,
      showReviewer: false,
      // 表单提交字段
      temp: {
        id: undefined,
        resourceNo:'',
        parentResourceName: '',
        parentResourceNo: '',
        resourceName: '',
        resourceType: '',
        requestPath: '',
        label: '',
        sortNo: '',
        icon: '',
        remark:'',
        dataState: '',
        checkedIds: []
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑资源',
        create: '添加资源'
      },
      rules: {
        resourceNo: [{ required: true, message: '必填', trigger: 'blur' }],
        resourceName: [{ required: true, message: '必填', trigger: 'blur' }],
        resourceType: [{ required: true, message: '必填', trigger: 'change' }],
        requestPath: [{ required: true, message: '必填', trigger: 'blur' }],
        label: [{ required: true, message: '必填', trigger: 'blur' }],
        icon: [{ required: true, message: '必填', trigger: 'blur' }],
        sortNo: [{ required: true, message: '必填', trigger: 'blur' }]
      },
      downloadLoading: false
    }
  },

  created() {
    this.getList();
    // 初始化树形资源
    this.initTree()
  },
  watch: {
    filterText(val) {
      this.$refs.resourceTree.filter(val);
    }
  },
  methods: {
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    createResourceNo(op){
      createResourceNo(op).then(response => {
        this.temp.resourceNo=response.data;
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
    formatResourceType(row){
      for(let x of this.calendarResourceTypeOptions){
        if (row.resourceType === x.dataValue) {
          return x.discriptioin;
        }
      }
    },
    initTree() {
      initTree(this.treeQuery).then(response => {
        console.log('查询资源树返回:' + response.data);
        this.tree.data = response.data.items;
        this.tree.expandedIds = response.data.expandedIds
        this.treeQuery.treeList= response.data.items;
      })
    },
    getList() {
      this.listLoading = true;
      fetchList(this.listQuery).then(response => {
        this.list = response.data.records;
        this.total = parseInt(response.data.total);
        console.log('查询资源信息返回:' + response.data);
        // Just to simulate the time of the request
        setTimeout(() => {
          this.listLoading = false
        }, 1 * 1000)
      })
    },
    handleNodeClick(data) {
      this.listQuery.parentResourceNo = data.id;
      this.listQuery.parentResourceName = data.label;
      this.getList()
    },
    // 重新刷新页面
    handleFilter() {
      this.listQuery.pageNum = 1;
      this.getList()
    },
    handleAll(){

    },
    // 重置表单
    resetTemp() {
      this.temp = {
        id: "",
        resourceNo:'',
        parentResourceName: '',
        parentResourceNo: '',
        resourceName: '',
        resourceType: '',
        requestPath: '',
        label: '',
        sortNo: '',
        icon: '',
        remark:'',
        dataState: '',
        checkedIds: []
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
      this.temp = Object.assign({}, rows[0]) ;// copy obj
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
