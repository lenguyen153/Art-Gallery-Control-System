import router from '../router'
import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

function artworkAddUpdateButton() {
    this.name = ''
    this.des = ''
    this.artstyle = ''
    this.nbcopy = 1
    this.price = 0
}


function UserDto(name, username, password){
    this.name = name
    this.username = username
    this.password = password
}

function ArtworkDto(artist, name, description, artstyle, nbCopy, price) {
    this.artist = artist;
	this.name = name;
	this.description = description;
	this.artStyle = artstyle;
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
function CustomerDto(name, username, password, address, id) {
    this.name = name;
    this.username = username;
    this.password = password;
    this.address = address;
    this.id = id;
}
function TransactionDto(price, id, commission, tax, date) {
    this.price = price;
    this.id = id;
    this.commissionApplied = commission;
    this.taxApplied = tax;
    this.date = date;
}

var isArtworkViewed = false
export default{

    data () {
        return {
            artist: null,               // the artist logged in
            artworks: [],
            transactions: [],
            errorAddArtwork: '' , 
            errorUpdateArtwork: '' ,
            errorDeleteArtwork: '',
            errorUpdateAccount: '' ,
            errorTransaction: '',
            username: 'artist1',               // the logged in artist's username 

            // variable for displaying/hiding elements on page
            view: {artworks: false,
                    transactions: false},
            // hold all inputs for adding an artwork
            addArtworkButton : new artworkAddUpdateButton(),

            //hold all inputs for updating an artwork
            updateArtworkButton: new artworkAddUpdateButton(),

            updateAccountButtons: {
                name: '',
                pswd: '',
                shipToGallery: ''
            }
        }
    },
    created: function(){
        /** to be changed */
        const a1 = new ArtistDto('name1', 'username1', 'password1', 'id1', 'null')
        const a2 = new ArtworkDto(a1, 'name1', 'description1', 'MODERN', '1', '100')
        const a3 = new ArtworkDto(a1, 'name2', 'description2', 'CLASSICAL', '2', '300')
        this.artworks = [a2, a3]
        const t1 = new TransactionDto("150", "1", "5%", "15%", "12-01-2020")
        const t2 = new TransactionDto("2000", "2", "5%", "15%", "11-26-2020")
        this.transactions = [t1 ,t2]

        AXIOS.get('getArtist/'.concat(this.username))
        .then(response => {
            this.artworks = response.data.artworks
        })
        .catch(e => {
            console.log(e)
        })
        
        
    },
    methods: {
        logout: function() {
            this.$store.commit('change', '')
            router.push({
                name: "Home"
            });
        },
        viewArtworksFromArtist: function (username) {
            // this.view.artworks = !this.view.artworks
            this.artworks = []
            if (isArtworkViewed == false){
              AXIOS.get('/AllArtworks/').then(response => {
                this.artworks = response.data
                isArtworkViewed = true
              })
                .catch(e => {
                  var errorMsg = e.message
                  console.log(errorMsg)
                });
              }else {
                this.artworks = []
                isArtworkViewed = false
              }   
        },

        viewTransactions: function() {
            this.view.transactions = !this.view.transactions
        },
        viewArtworks: function() {
            this.view.artworks = !this.view.artworks
        },
        addArtwork: function (name, des, style, nbcopy, price) {
            // var artworkToAdd = new ArtworkDto(this.artist, name, des, style, nbcopy, price)
            this.artworks = []
            const params = JSON.stringify({
                "name": name,
                "description": des,
                "nbCopy": nbcopy,
                "price": price,
                "artStyle": style
                });
            AXIOS.post('/createArtwork/'.concat(this.username), artworkToAdd, {})
            .then(response => {
                this.artist.artworks.push(response.data) //not sure if needed
                this.artworks.push(response.data)
                this.errorAddArtwork = ''
                this.addArtworkButton = new artworkAddUpdateButton()
            })
            .then(response => {
                this.artist.artworks.push(response.data) //not sure if needed
                this.artworks.push(response.data)
                this.errorAddArtwork = ''
                this.addArtworkButton = new artworkAddUpdateButton()
            // AXIOS.post('/createArtwork/'.concat(this.username),params,{
            //     "headers": {
            //     "content-type": "application/json",    
            //     },
            // })
            // .then(response => {
            //     viewArtworksFromArtist(username)
            //     this.errorAddArtwork = ''
            //     // this.addArtworkButton = new artworkAddUpdateButton()
            })
            .catch(e => {
                var errorMsg = e
                console.log(errorMsg)
                this.errorAddArtwork = errorMsg
                // this.addArtworkButton = new artworkAddUpdateButton()
            })
        },

        deleteArtwork: function(artworkName) {
            AXIOS.delete('/deleteArtwork'.concat(artworkName), {}, {})
            .then(response => {
                // find the index of the artwork with artwork.name == artworkName
                const index = this.artworks.map(x => x.name).indexOf(artworkName)
                // if in this.artworks
                if (index > -1) {
                    this.artworks.splice(index, 1)  // remove it
                    this.errorDeleteArtwork = ''
                }
            })
            .catch(e => {
                var errorMsg = e
                console.log(errorMsg)
                this.errorDeleteArtwork = errorMsg
            })
        },

        updateArtwork: function(artworkName, des, style, nbcopy, price) {
            AXIOS.put('/updateArtwork/', {}, {
                params: {
                    name: artworkName,
                    description: des,
                    artstyle: style,
                    nbcopy: nbcopy,
                    price: price
                }
            })
            .then(response => {
                // find the index of the artwork with artwork.name == artworkName
                const index = this.artworks.map(x => x.name).indexOf(artworkName)
                var artwork = this.artworks[index]
                artwork.description = des
                artwork.artstyle = style
                artwork.nbCopy = nbcopy
                artwork.price = price
                this.errorUpdateArtwork = ''
                this.updateArtworkButton = artworkAddUpdateButton()
            })
            .catch(e => {
                var errorMsg = e
                console.log(errorMsg)
                this.errorUpdateArtwork = errorMsg
                this.updateArtworkButton = artworkAddUpdateButton()
            })
        },

        changeName: function (name) {
            // Create dto with everything same value for the artist logged in except the name which
            // will be changed
            var temp_userDto = new UserDto(name, this.username, this.artist.password)
            // pass nearly identical dto so only name is changed
            AXIOS.put('/updateUser', temp_userDto, {})
            .then(response => {
                this.artist.name = name
                this.errorUpdateAccount = ''
                this.updateAccountButtons.name = ''
            })
            .catch(e => {
                var errorMsg = e
                console.log(errorMsg)
                this.errorUpdateAccount = errorMsg
                this.updateAccountButtons.name = ''
            })
        },

        changePassword: function (pswd) {
            // Create dto with everything same value for the artist logged in except the pswd which
            // will be changed
            var temp_userDto = new UserDto(this.artist.name, this.username, pswd)
            // pass nearly identical dto so only pswd is changed
            AXIOS.put('/updateUser', temp_userDto, {})
            .then(response => {
                this.artist.password = pswd
                this.errorUpdateAccount = ''
                this.updateAccountButtons.pswd = ''
            })
            .catch(e => {
                var errorMsg = e
                console.log(errorMsg)
                this.errorUpdateAccount = errorMsg
                this.updateAccountButtons.pswd = ''
            })
        },

        changeShipping: function (isShipped) {
           AXIOS.put('/updateShipmentArtist', {}, {
               params: {
                   username: this.username,
                   isShippedStr: isShipped
               }
           })
           .then(response => {
               this.artist.isShipped = isShipped
               this.errorUpdateAccount = ''
               this.updateAccountButtons.shipToGallery = ''
           })
           .catch(e => {
               var errorMsg = e
               console.log(errorMsg)
               this.errorUpdateAccount = errorMsg
               this.updateAccountButtons.shipToGallery = ''
           })
        }
    }
    
}