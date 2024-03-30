import router from '../router'
import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

function ArtworkDto(artist, name, description, artstyle, nbCopy, price) {
    this.artist = artist;
    this.name = name;
    this.description = description;
    this.artstyle = artstyle;
    this.nbCopy = nbCopy;
    this.price = price;
}
function ArtistDto(name, username, password, id, artworks) {
    this.name = name;
    this.username = username;
    this.password = password;
    this.id = id;
    this.artworks = artworks;
}

export default {

    data() {
        return {
            artworks: [],
            errorCheckout: "",
            Username: "gkontoCustomer",
            cart: ""
        }
    },
    created: function () {
        /** to be changed */
        //const a1 = new ArtistDto('name1', 'username1', 'password1', 'id1', 'null')
        //const a2 = new ArtworkDto(a1, 'name1', 'description1', 'artstyle1', '1', '1')
        //const a3 = new ArtworkDto(a1, 'name2', 'description2', 'artstyle2', '2', '2')
        //this.artworks = [a2, a3]
        /** not to be changed */
        // var arr = []
        // Initializing from backend
        var req = AXIOS.get('/'+Username+'/cart')
            .then(response => {
                // JSON responses are automatically parsed.
                this.cart = response.data
                this.artworks = []
                this.artworks = response.data.artworks
            })
            .catch(e => {
                this.errorManager = e;
            });
        this.username = this.$store.getters.username
    },
    methods: {
        returnToCustomerPage: function () {
            router.push({
                name: "CustomerPage"
            });
        },
        checkout: function () {
            AXIOS.get('/'+Username+'/cart')
            .then(response => {
                // JSON responses are automatically parsed.
                this.cart = response.data
            })
            .catch(e => {
                this.errorManager = e;
            });
            AXIOS.post('/createTransactionsFromCart/' + cart.id)
                .then(response => {
                    // JSON responses are automatically parsed.
                    //no response needed
                })
                .catch(e => {
                    this.errorManager = e;
                });
            router.push({
                name: "CheckoutPage"
            });
        },

        removeArtwork: function (artworkName) {
            AXIOS.post("/removeFromCart", { params: { username: username, artName: artworkName } })
                .then(response => {
                    // JSON responses are automatically parsed.
                    //no response is really needed
                    this.cart = response.data
                })
                .catch(e => {
                    var errorMsg = e
                    console.log(errorMsg)
                })
        }
    }
}