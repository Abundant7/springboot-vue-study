import vue from 'vue'
import Vuex from 'vuex'
import router,{resetRouter} from "../router";
vue.use(Vuex)

function addNewRoute(menuList){

    console.log(menuList)
    let routes = router.options.routes
    console.log(routes)
    routes.forEach(routeItem=>{
        if(routeItem.path=="/Index"){
            menuList.forEach(menu=>{
                let childRoute =  {
                    path:'/'+menu.menuclick,
                    name:menu.menuname,
                    meta:{
                        title:menu.menuname
                    },
                    component:()=>import('../components/'+menu.menucomponent)
                }

                routeItem.children.push(childRoute)
            })
        }
    })

    resetRouter()
    router.addRoutes(routes)

}

export default new Vuex.Store({
    state: { // 存放数据 和data类似
        menu:[]
    },
    mutations: { // 用来修改state和getters里面的数据
        setMenu(state,menuList){
            state.menu = menuList

            addNewRoute(menuList)
        }
    },
    getters: { // 相当于计算属性
        getMenu(state){
            return state.menu
        }
    },
    actions: { // vuex中用于发起异步请求
    },
    modules: {// 拆分模块
    }
  })


  