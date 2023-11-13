<template>
  <div class="app-container">
    <el-container>
      <el-main>
        <div class="filter-container">

	<#foreach column in table.columns>
	  <#if column.queryControl=="input" >
          <el-input
            v-model="listQuery.${column.property}"
            :placeholder="$t('table.${table.varName}.${column.property}')"
            style="width: 200px;"
            class="filter-item"
            @keyup.enter.native="handleFilter"
          />
	  </#if>
        </#foreach> 

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
            @click="handleUpdate">
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
          <#foreach column in table.columns>
	  <#if column.listControl=="text" >
	  <el-table-column :label="$t('table.${table.varName}.${column.property}')" prop="${column.property}" min-width="110px" />
	  </#if>	 
	  </#foreach> 
        </el-table>

        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="listQuery.pageNum"
          :limit.sync="listQuery.pageSize"
          @pagination="getList"
        />

        <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" width="60%" top="10px">
          <el-container>          
            <el-main>
              <el-form
                ref="dataForm"
                :rules="rules"
                :model="temp"
                label-position="right"
                label-width="90px"
                size="small"
                style="width: 500px; margin-left:30px;"
              >
	     <#foreach column in table.columns>
	        <#if column.key==true  >
		  <el-form-item v-show="false" prop="${column.property}">
		      <el-input v-model="temp.${column.property}" />
		  </el-form-item>
		<#else>
		    <#if column.editControl=="input" >
			<el-form-item :label="$t('table.${table.varName}.${column.property}')" prop="${column.property}">
			  <el-input v-model="temp.${column.property}" :placeholder="$t('table.${table.varName}.${column.property}')" />
			</el-form-item>	
		    </#if>
		</#if>
	       </#foreach> 
	     </el-form>
            </el-main>
          </el-container>

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
import { fetchList, createInfo, updateInfo } from '@/api/${db.name}/${table.name}'
import waves from '@/directive/waves' // waves directive
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

const calendarDataStateOptions = [
  {"dataValue":"0","discriptioin":"正常"},
  {"dataValue":"1","discriptioin":"禁用"},];

export default {
  name: 'ComplexTable',
  components: { Pagination },
  directives: { waves },
  data() {
    return {
      tableKey: 0,
      // 定义列表
      list: null,
      total: 0,
      listLoading: true,
      // 查询条件
      listQuery: {
      <#foreach column in table.columns>
	${column.property}: null,
      </#foreach> 
        pageNum: 1,
        pageSize: 10,
        dataState: null
      },
      showReviewer: false,
      // 表单提交字段
      temp: {      
	<#foreach column in table.columns>	
	${column.property}: null,
        </#foreach> 
        checkedResourceNos:[],
        checkedDeptNos:[]
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑角色',
        create: '添加角色'
      },
      rules: {
        <#foreach column in table.columns>	
	   <#if column.key==false  >
	      <#if column.editControl=="input" >
              ${column.property}: [{ required: true, message: '必填', trigger: 'blur' }],
	      </#if>
	   </#if>
        </#foreach> 
        dataState0: [{ required: true, message: '必填', trigger: 'blur' }]
      },
      downloadLoading: false
    }
  },
  created() {
    this.getList();
  },
  methods: {   
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
    getList() {
      this.listLoading = true;
      fetchList(this.listQuery).then(response => {
        this.list = response.data.records;
        this.total = parseInt(response.data.total);
        console.log('查询角色信息返回:' + response.data);
        // Just to simulate the time of the request
        setTimeout(() => {
          this.listLoading = false
        }, 1 * 1000)
      })
    },
    // 重新刷新页面
    handleFilter() {
      this.listQuery.pageNum = 1;
      this.getList()
    },
    // 重置表单
    resetTemp() {
      this.temp = {
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
            //this.temp = response.data;
            this.list.push(this.temp);
            this.dialogFormVisible = false;
            this.$message({
              message: '保存成功',
              type: 'success',
              duration: 1500,
              offset: 300
            })
          })
        }
      })
    },
    handleUpdate() {
      this.resetTemp();
      const rows = this.$refs.multipleTable.selection
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
      })
    },
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          updateInfo(this.temp,this.temp.${table.key}).then(() => {
            const index = this.list.findIndex(v => v.id === this.temp.${table.key})
            this.list.splice(index, 1, this.temp)
            this.dialogFormVisible = false
            this.$message({
              message: '修改成功',
              type: 'success',
              duration: 1500,
              offset: 300
            })
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
