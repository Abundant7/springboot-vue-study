<template>
    <el-menu 
    style="height: 100vh;" 
    background-color="#545c64" 
    text-color="#fff" 
    active-text-color="#ffd04b"
    default-active="/a"
    :collapse="isCollapse"
    :collapse-transition="false"
    >
        <el-menu-item index="/a">
            <i class="el-icon-star-on"></i>
            <span slot="title">首页</span>
        </el-menu-item>
        <el-menu-item index="/b">
            <i class="el-icon-star-on"></i>
            <span slot="title">崔佛</span>
        </el-menu-item>
        <el-menu-item index="/c">
            <i class="el-icon-star-on"></i>
            <span slot="title">麦克</span>
        </el-menu-item>
    </el-menu>
</template>

<script>
export default {
    name: 'Aside',
    data(){
        return{
            //isCollapse:true
        }
        
    },
    props:{
        isCollapse:Boolean
    }
}
</script>

<style scoped></style>