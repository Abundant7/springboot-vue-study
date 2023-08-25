<template>
    <div style="display: flex;line-height: 60px;">
        <div style="font-size: 20px;">
            <i :class="icon" @click="collapse"></i>
        </div>
        <div style="flex: 1%;text-align: center;font-size: 34px;">
            <span>仓储管理系统</span>
        </div>
        <el-dropdown>
            <span>{{ user.name }}</span>
            <i class="el-icon-arrow-down" style="margin-left: 5px"></i>
            <el-dropdown-menu slot="dropdown">
                <el-dropdown-item @click.native="toUser">个人信息</el-dropdown-item>
                <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>

            </el-dropdown-menu>
        </el-dropdown>

    </div>
</template>

<script>
import { Collapse } from 'element-ui';

export default {
    name: "Header",
    data(){
        return{
            user : JSON.parse(sessionStorage.getItem('CurUser'))
        }
    },
    props:{
        icon:String
    },
    methods: {
        toUser() {
            console.log('toUser')
            this.$router.push("/Home")
        },
        logout() {
            console.log('logout')
            this.$confirm('确认退出登录吗？', '提示', {
                confirmButtonText: '确定',
                type: 'warning',
                center: true,
            })
                .then(() =>{
                    this.$message({
                        type:'success',
                        message:'退出登录成功'
                    })

                    this.$router.push("/")
                    sessionStorage.clear()
                })
                .catch(()=>{
                    this.$message({
                        type:'info',
                        message:'取消退出登录'
                    })
                })


        },
        collapse() {
            this.$emit('doCollapse')
        }
    },
    
}
</script>

<style scoped></style>