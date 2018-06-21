import {
    LAYOUT_INLOADING,
    LAYOUT_UNLOADING
} from '../mutations-type';

const state = {
    loading: false
};

const mutations = {
    [LAYOUT_INLOADING](state){
        state.loading = true;
    },
    [LAYOUT_UNLOADING](){
        state.loading = false;
    }
}

const namespaced = true;

export default{
    state,
    mutations,
    namespaced
}