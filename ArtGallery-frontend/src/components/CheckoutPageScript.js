import router from '../router'
export default{
    methods: {
        returnToCustomerPage: function() {
            router.push({
            name: "CustomerPage"
            });
        }, 
        exit: function() {
            this.$store.commit('change', '')
            router.push({
                name: "Home"
            });
        },  
    }
}