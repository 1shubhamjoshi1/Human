
var config = {
  apiKey: "AIzaSyCnthmPVf-LprbEkBg_y6aU6C53cmtarrE",
  authDomain: "humanjiit.firebaseapp.com",
  databaseURL: "https://humanjiit.firebaseio.com",
  storageBucket: "humanjiit.appspot.com",
  messagingSenderId: "507854496028"
};
firebase.initializeApp(config);

const proObj = document.getElementById('box');
const dbRefObj = firebase.database().ref().child('posts1');
dbRefObj.on('child_added', function(childsnapshot){
  var showCase = document.getElementById('show');
  var image = document.createElement('img');
  var link = JSON.stringify(childsnapshot.val().imageUrl);
  console.log();

  image.setAttribute('src', link.substr(1,link.length-2));
  image.setAttribute('height','520px');
  image.setAttribute('width','520px');
  showCase.appendChild(image);
});
