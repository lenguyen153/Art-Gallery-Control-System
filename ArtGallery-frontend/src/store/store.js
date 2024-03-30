import Vue from "vue";
import Vuex from "vuex";
 
Vue.use(Vuex);
 
export const store = new Vuex.Store({
    state: {
        username: '',
    },
    mutations: {
        change(state, username) {
            state.username = username
        }
    },
    getters: {
        username: state => state.username
    }
   
})