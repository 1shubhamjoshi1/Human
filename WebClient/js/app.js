
$(window).load(function() {
		// Animate loader off screen
		$(".se-pre-con").fadeOut("slow");;
	});

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

  var parent = document.createElement('div');
  var curtain = document.createElement('div');
  var image = document.createElement('img');
  var link = JSON.stringify(childsnapshot.val().imageUrl);
  console.log();

  image.setAttribute('src', link.substr(1,link.length-2));
  image.setAttribute('height','150px');
  image.setAttribute('background-size','cover');
  image.setAttribute('display','inline-block');

  image.style.backgroundSize = "150px 100px";
  image.setAttribute('width','150px');
  image.style.position="relative";


  curtain.style.backgroundColor="black";
  curtain.setAttribute('height','150px');
  curtain.setAttribute('width','150px');
  curtain.setAttribute("id", "curtain");
  curtain.innerHTML="Hello";

  curtain.style.width="150px";
  curtain.style.height="150px";
  curtain.style.position="absolute";
  curtain.style.marginBottom="-250px"
  parent.style.width="150px";
  parent.style.height="150px";
  parent.style.marginRight = "5px";
  parent.style.marginBottom="5px";
  parent.style.display="inline-block";

    parent.insertBefore(image,parent.firstChild);
     parent.insertBefore(curtain,parent.firstChild);


    image.style.opacity="0.5";

  showCase.insertBefore(parent,showCase.firstChild);
  });
