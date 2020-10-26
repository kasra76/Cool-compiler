class Board inherits IO {

 rows : Int;
 columns : Int;
 board_size : Int;

 size_of_board(initial : String) : Int {
   initial.length()
 };

 board_init(start : String) : SELF_TYPE {
   (let size :Int  <- size_of_board(start) in
    {
	if size = 15 then
	 {
	  rows <- 3;
	  columns <- 5;
	  board_size <- size;
	 }
	else if size = 16 then
	  {
	  rows <- 4;
	  columns <- 4;
	  board_size <- size;
	 }
	else if size = 20 then
	 {
	  rows <- 4;
	  columns <- 5;
	  board_size <- size;
	 }
	else if size = 21 then
	 {
	  rows <- 3;
	  columns <- 7;
	  board_size <- size;
	 }
	else if size = 25 then
	 {
	  rows <- 5;
	  columns <- 5;
	  board_size <- size;
	 }
	else if size = 28 then
	 {
	  rows <- 7;
	  columns <- 4;
	  board_size <- size;
	 }
	else 	-- If none of the above fit, then just give
	 {  -- the configuration of the most common board
	  rows <- 5;
	  columns <- 5;
	  board_size <- size;
	 }
	fi fi fi fi fi fi;
	self;
    }
   )
 };

};
