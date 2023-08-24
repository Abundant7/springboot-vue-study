<template>
    <el-container style="height: 100%; border: 1px solid #eee">
        <el-aside :width="aside_width" style="height: 100vh;background-color: rgb(238, 241, 246)">
            <Aside :isCollapse="isCollapse"></Aside>
        </el-aside>

        <el-container style="height: 100%">
            <el-header style="height: 100%;text-align: right; font-size: 12px; border-bottom: rgb(19, 17, 17) 2px solid;">
                <Header @doCollapse="doCollapse" :icon="icon"></Header>
            </el-header>

            <el-main style="height: 100%">
                <Main></Main>
            </el-main>
        </el-container>
    </el-container>
</template>

<script>
import Aside from './Aside.vue'
import Header from './Header.vue'
import Main from './Main.vue'
export default {
    name: "Index",
    components: { Aside, Header, Main },
    data () {
        return {
            isCollapse:false,
            aside_width:"200px",
            icon:'el-icon-s-unfold'
        }
    },
    methods:{
        doCollapse(){
            this.isCollapse = !this.isCollapse
            if(this.isCollapse){
                this.aside_width = '64px'
                this.icon = 'el-icon-s-fold'
            }else{
                this.aside_width = '200px'
                this.icon = 'el-icon-s-unfold'
            }
        }

    }


}
</script>

<style>
.el-header {
    background-color: #daba09;
    color: #333;
    line-height: 60px;
}

.el-aside {
    background-color: #202a4b;
    color: #ac0c0c;
}
</style>