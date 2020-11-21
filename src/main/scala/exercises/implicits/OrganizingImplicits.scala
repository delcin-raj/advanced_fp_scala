package exercises.implicits

object OrganizingImplicits extends App {
  case class Purchase(nUnits: Int, unitPrice: Double) {
    val totalPrice = nUnits * unitPrice
  }
  object Purchase {
    implicit val totalPriceOrdering: Ordering[Purchase] = Ordering.fromLessThan{
      (x,y) => x.totalPrice < y.totalPrice
    }
  }
  object PurchaseQuantity {
    implicit val quantityOrdering: Ordering[Purchase] = Ordering.fromLessThan{
      (x,y) => x.nUnits < y.nUnits
    }
  }
  object PurchaseUnitPrice {
    implicit val priceOrdering: Ordering[Purchase] = Ordering.fromLessThan{
      (x,y) => x.unitPrice < y.unitPrice
    }
  }

}
