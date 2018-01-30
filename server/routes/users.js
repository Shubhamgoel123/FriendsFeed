var express=require('express');
var router=express.Router(); 
var passport =require('passport');
var LocalStrategy = require('passport-local').Strategy;
var User=require('../models/user');
User.on('index',error =>console.error(error));
//Register

var j="yoyo";

router.get('/register',function(req,res){
	//res.render('registerscreen.ejs');
	res.send(j);
	console.log("register");
});

//Login
router.get('/login',function(req,res){
	//res.render('loginscreen.ejs');
	res.send(j);
	console.log("login");
});

//Register User
router.post('/register',function(req,res){
	var name=req.body.name;
	var email=req.body.email;
	var username=req.body.username;
	var password=req.body.password;
	var password2=req.body.password2;
	var image="";
	
	//validation
	req.checkBody('name','Name is required').notEmpty();
	req.checkBody('email','Email is required').notEmpty();
	req.checkBody('email','Email is not valid').isEmail();
	req.checkBody('username','UserName is required').notEmpty();
	req.checkBody('password','Password is required').notEmpty();
	req.checkBody('password2','Passwords do not match').equals(req.body.password);
	
	var errors=req.validationErrors();
	
	if(errors)
	{
		return res.status(440).send();
	}
	else
	{
		var newUser=new User({
			name:name,
			email:email,
			username:username,
			password:password,
			image:image
		});
	 
	    User.createUser(newUser,function(err, user){
           if(err) throw err;
           console.log(user);

           j=user.id;
           
	    });
	 
	  res.redirect('/users/register');
	  return res.status(200).send();
	}
});

passport.use(new LocalStrategy(
     function(username, password, done)
     {
       User.getUserByUsername(username,function(err,user)
       {
         if (err)
         	throw err;
         if(!user){
         	return done(null, false, {message: 'Unknown User'});
              }
         User.comparePassword(password,user.password,function(err, isMatch){
             if(err) throw err;
             if(isMatch){
             	j=user.id;
             	return done(null,user);
         }
         else{
         	return done(null,false,{message: 'Invalid password'});
         }
         });
       });
     }));

passport.serializeUser(function(user, done){
 done(null,user.id);
});

passport.deserializeUser(function(id, done){
  	User.getUserById(id, function(err,user){
       done(err, user);
  	});
});




router.post('/login',
	passport.authenticate('local',{successRedirect:'/users/login',failureRedirect:'/users/login' ,failureFlash: true}),
     function(req,res){
        //res,redirect('/');
        return res.status(200).send();
     });

router.get('/logout',function(req,res){
	req.logout();
	//req.flash('success_msg','You are logged out');
	//res.redirect('/');
})

router.get('/profileimage/:_id',function(req,res){
	//res.render('registerscreen.ejs');
	User.getUserById(req.params._id,function(err,user){

			if(err)
			{
				throw err;
			}

			res.json(user);
			console.log(user.name);

		});
});

router.put('/profileimage/:_id',function(req,res)
{
		var id=req.params._id;
		var user=req.body;
		User.updateUser(id,user,{},function(err,user){

			if(err)
			{
				return res.status(440).send;
			}

			return res.status(200).send;

		});
});


module.exports= router;

