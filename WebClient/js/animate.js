var b = document.getElementById('next');

b.onclick = function(event){
  var div = document.getElementById('top-section');
  var photo = document.getElementById('button');
  var name = document.getElementById('intro');
  photo.style.height="0px";
  photo.style.width="0px";
  name.style.opacity="0";
  div.style.height="0px"
  console.log('s');
};
