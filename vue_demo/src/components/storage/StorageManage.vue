<template>
    <div>
      <div style="margin-bottom: 5px;">
        <el-input v-model="name" placeholder="请输入仓库名" suffix-icon="el-icon-search" style="width:200px"
          @keyup.enter.native="loadPost"></el-input>
  
        
  
        <el-button type="primary" style="margin-left: 5px;" @click="loadPost">查询</el-button>
        <el-button type="success" style="margin-left: 5px;" @click="resetParam">重置</el-button>
        <el-button type="info" style="margin-left: 5px;" @click="add">新增</el-button>
      </div>
      <el-table :data="tableData" :header-cell-style="{ background: '#eef1f6', color: '#606266' }" border>
        <el-table-column prop="id" label="ID" width="140">
        </el-table-column>
        <el-table-column prop="name" label="名称" width="120">
        </el-table-column>
        <el-table-column prop="remark" label="备注" width="120">
        </el-table-column>
        
        <el-table-column prop="operate" label="操作">
          <template slot-scope="scope">
            <el-button size="small" type="success" @click="mod(scope.row)">编辑</el-button>
  
            <el-popconfirm title="确定删除吗？" @confirm="del(scope.row.id)">
              <el-button slot="reference" size="small" type="danger" style="margin-left: 5px;">删除</el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
  
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum"
        :page-sizes="[2, 5, 10, 15]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
  
      <el-dialog title="提示" :visible.sync="dialogVisible" width="30%">
  
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          
          <el-form-item label="名字" prop="name">
            <el-col :span="15">
              <el-input v-model="form.name"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="备注" prop="remark">
            <el-col :span="15">
              <el-input v-model="form.remark"></el-input>
            </el-col>
          </el-form-item>
  
          
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="save">确 定</el-button>
        </span>
      </el-dialog>
  
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    name: "StorageManage",
    data() {
       
      return {
        tableData: [],
        pageSize: 2,
        pageNum: 1,
        total: 0,
        name: '',
        remark: '',
        
        dialogVisible: false,
        form: {
          id: '',
          name: '',
          remark: ''
          
        },
        rules: {
          
        }
      }
    },
    methods: {
      loadGet() {
  
        axios.get('http://localhost:8090/storage/list').then(res => res.data).then(res => {
          console.log(res)
          this.tableData = res
        })
  
      },
  
      loadPost() {
        axios.post('http://localhost:8090/storage/listPage', {
          pageSize: this.pageSize,
          pageNum: this.pageNum,
          param: {
            name: this.name,
            remark: this.remark,
          }
        }).then(res => res.data).then(res => {
          console.log(res)
          if (res.code == 200) {
            this.tableData = res.data
            this.total = res.total
          } else {
            alert('获取数据失败')
          }
  
        })
  
      },
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
        this.pageSize = val
        this.loadPost();
      },
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`);
        this.pageNum = val
        this.loadPost();
      },
      resetParam() {
        this.name = ''
        this.remark = ''
        this.loadPost();
      },
      resetForm() {
        this.$refs.form.resetFields();
      },
      add() {
        this.dialogVisible = true
        this.$nextTick(() => {
          this.resetForm()
        })
  
  
  
      },
      doSave() {
        axios.post('http://localhost:8090/storage/save', this.form
        ).then(res => res.data).then(res => {
          console.log(res)
          if (res.code == 200) {
            this.$message({
              message: '操作成功',
              type: 'success'
            });
            this.dialogVisible = false
            this.loadPost();
          } else {
            this.$message.error('操作失败');
          }
  
        })
  
      },
      doMod() {
        axios.post('http://localhost:8090/storage/update', this.form
        ).then(res => res.data).then(res => {
          console.log(res)
          if (res.code == 200) {
            this.$message({
              message: '操作成功',
              type: 'success'
            });
            this.dialogVisible = false
            this.loadPost();
          } else {
            this.$message.error('操作失败');
          }
  
        })
  
      },
      save() {
  
        this.$refs.form.validate((valid) => {
          if (valid) {
  
            if (this.form.id) {
              this.doMod();
            }
            else {
              this.doSave();
            }
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      del(id) {
        console.log(id)
  
        axios.get('http://localhost:8090/storage/del?id=' + id
        ).then(res => res.data).then(res => {
          console.log(res)
          if (res.code == 200) {
            this.$message({
              message: '操作成功',
              type: 'success'
            });
            this.dialogVisible = false
            this.loadPost();
          } else {
            this.$message.error('操作失败');
          }
  
        })
  
      },
      mod(row) {
        console.log(row)
  
  
  
        this.dialogVisible = true
  
        this.$nextTick(() => {
          this.form.id = row.id
          
          this.form.name = row.name
          this.form.remark = row.remark
          
        })
      }
  
    },
    beforeMount() {
      //this.loadGet();
      this.loadPost();
    }
  
  
  }
  </script>
  
  <style scoped></style>