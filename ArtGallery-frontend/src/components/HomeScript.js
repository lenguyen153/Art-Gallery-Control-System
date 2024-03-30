import router from '../router'
export default{
    methods: {
        goToCustomerLoginPage: function() {
            router.push({
            name: "CustomerLogin"
            });
        }, 
        goToArtistLoginPage: function() {
            router.push({
            name: "ArtistLogin"
            });
        },
        goToManagerLoginPage: function() {
            router.push({
            name: "ManagerLogin"
            });
        },
        goToCreateCustomerPage: function() {
            router.push({
            name: "CreateCustomer"
            });
        }, 
        goToCreateArtistPage: function() {
            router.push({
            name: "CreateArtist"
            });
        }, 
        goToCreateManagerPage: function() {
            router.push({
            name: "CreateManager"
            });
        }   
    }
}

