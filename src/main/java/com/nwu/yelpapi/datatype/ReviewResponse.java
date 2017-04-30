package com.nwu.yelpapi.datatype;

import java.io.Serializable;
import java.util.List;

public class ReviewResponse implements Serializable {
   //---------------------------------------------------------------------------
   public final int total;
   public final List<Review> reviews;

   //---------------------------------------------------------------------------
   public ReviewResponse(int total, List<Review> reviews) {
      this.total = total;
      this.reviews = reviews;
   }

   //---------------------------------------------------------------------------
}
