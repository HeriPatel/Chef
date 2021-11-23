<?php
	include("conn.php");

	$catName = $_GET['catName'];
	class response
	{
		public $subCatName;
		public $subCatImg;
		public $cat_name;
	}
	

	$q2 = 'select Category_ID from category where name = "'.$catName.'"';
	$result2 = mysqli_query($con,$q2);
	$row2 = mysqli_fetch_assoc($result2);

	$q = "select * from sub_category where cat_id = ".$row2['Category_ID'];

	$result=mysqli_query($con,$q);
	$arr = array();
	$i = 0;
	
	$row = mysqli_num_rows($result);

	  while($row = mysqli_fetch_assoc($result)) {
		$r = new response();
		$r->subCatName = $row['name'];
		$r->subCatImg = $row['image'];
		$r->cat_name = $catName;
		$arr[$i] = $r;
		$i = $i + 1;
	  }
	  echo json_encode($arr);
?>