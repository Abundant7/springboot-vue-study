<template>
    <div>
      <div style="margin-bottom: 5px;">
        <el-input v-model="name" placeholder="请输入名称" suffix-icon="el-icon-search" style="width:200px"
          @keyup.enter.native="loadPost"></el-input>
  
        <el-select v-model="sex" filterable placeholder="请选择性别" style="margin-left: 5px;">
          <el-option v-for="item in sexs" :key="item.value" :label="item.label" :value="item.value">
          </el-option>
        </el-select>
  
        <el-button type="primary" style="margin-left: 5px;" @click="loadPost">查询</el-button>
        <el-button type="success" style="margin-left: 5px;" @click="resetParam">重置</el-button>
        <el-button type="info" style="margin-left: 5px;" @click="add">新增</el-button>
      </div>
      <el-table :data="tableData" :header-cell-style="{ background: '#eef1f6', color: '#606266' }" border>
        <el-table-column prop="id" label="ID" width="140">
        </el-table-column>
        <el-table-column prop="no" label="账号" width="120">
        </el-table-column>
        <el-table-column prop="name" label="姓名" width="120">
        </el-table-column>
        <el-table-column prop="age" label="年龄" width="120">
        </el-table-column>
        <el-table-column prop="sex" label="性别" width="120">
          <template slot-scope="scope">
            <el-tag :type="scope.row.sex === 1 ? 'primary' : 'success'" disable-transitions>{{ scope.row.sex === 1 ? '男' :
              '女'
            }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="phone" label="电话号码" width="120">
        </el-table-column>
        <el-table-column prop="roleId" label="角色" width="120">
          <template slot-scope="scope">
            <el-tag :type="scope.row.roleId === 0 ? 'danger' : (scope.row.roleId === 1 ? 'primary' : 'success')"
              disable-transitions>{{ scope.row.roleId === 0 ? '超级管理员' : (scope.row.roleId === 1 ? '管理员' : '用户') }}</el-tag>
          </template>
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
          <el-form-item label="账号" prop="no">
            <el-col :span="15">
              <el-input v-model="form.no"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="名字" prop="name">
            <el-col :span="15">
              <el-input v-model="form.name"></el-input>
            </el-col>
          </el-form-item>
  
          <el-form-item label="密码" prop="password">
            <el-col :span="15">
              <el-input v-model="form.password"></el-input>
            </el-col>
          </el-form-item>
  
          <el-form-item label="年龄" prop="age">
            <el-col :span="15">
              <el-input v-model="form.age"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="性别" prop="sex">
            <el-radio-group v-model="form.sex">
              <el-radio label="1">男</el-radio>
              <el-radio label="0">女</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="电话" prop="phone">
            <el-col :span="15">
              <el-input v-model="form.phone"></el-input>
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
    name: "UserManage",
    data() {
      let checkAge = (rule, value, callback) => {
        if (value > 150) {
          callback(new Error('年龄输⼊过⼤'));
        } else {
          callback();
        }
      };
      let checkDuplicate = (rule, value, callback) => {
        if (this.form.id) {
          return callback();
        }
        this.$axios.get("http://localhost:8090/user/findByNo?no=" + this.form.no).then(res => res.data).then(res => {
          if (res.code != 200) {
            callback()
          } else {
            callback(new Error('账号已经存在'));
          }
        })
      };
  
      return {
        tableData: [],
        pageSize: 2,
        pageNum: 1,
        total: 0,
        name: '',
        sex: '',
        sexs: [
          {
            value: '1',
            label: '男'
          }, {
            value: '0',
            label: '女'
          }
        ],
        dialogVisible: false,
        form: {
          id: '',
          name: '',
          no: '',
          password: '',
          age: '',
          phone: '',
          sex: '0',
          roleId: '2'
        },
        rules: {
          name: [
            { required: true, message: '请输入姓名', trigger: 'blur' },
            { min: 3, max: 8, message: '长度在 3 到 8 个字符', trigger: 'blur' }
          ],
          no: [
            { required: true, message: '请输入账号', trigger: 'blur' },
            { min: 3, max: 8, message: '长度在 3 到 8 个字符', trigger: 'blur' },
            { validator: checkDuplicate, trigger: 'blur' }
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            { min: 3, max: 8, message: '长度在 3 到 8 个字符', trigger: 'blur' }
          ],
          age: [
            { required: true, message: '请输⼊年龄', trigger: 'blur' },
            { min: 1, max: 3, message: '⻓度在 1 到 3 个位', trigger: 'blur' },
            { pattern: /^([1-9][0-9]*){1,3}$/, message: '年龄必须为正整数字', trigger: "blur" },
            { validator: checkAge, trigger: 'blur' }
          ],
          phone: [
            { required: true, message: "⼿机号不能为空", trigger: "blur" },
            {
              pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: "请输⼊正确的⼿机号码", trigger:
                "blur"
            }
          ]
        }
      }
    },
    methods: {
      loadGet() {
  
        axios.get('http://localhost:8090/user/list').then(res => res.data).then(res => {
          console.log(res)
          this.tableData = res
        })
  
      },
  
      loadPost() {
        axios.post('http://localhost:8090/user/listPage', {
          pageSize: this.pageSize,
          pageNum: this.pageNum,
          param: {
            name: this.name,
            sex: this.sex,
            roleId: '2'
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
        this.sex = ''
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
        axios.post('http://localhost:8090/user/save', this.form
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
        axios.post('http://localhost:8090/user/update', this.form
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
  
        axios.get('http://localhost:8090/user/del?id=' + id
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
          this.form.password = ''
          this.form.name = row.name
          this.form.no = row.no
          this.form.age = row.age + ''
          this.form.sex = row.sex + ''
          this.form.phone = row.phone
          this.form.roleId = row.roleId
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