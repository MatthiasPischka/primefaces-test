package org.primefaces.test;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.datatable.feature.DataTableFeatureKey;
import org.primefaces.component.datatable.feature.FilterFeature;
import org.primefaces.component.datatable.feature.SortFeature;
import org.primefaces.test.model.Product;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class DataTableRemoveBugView implements Serializable {

  private List<Product> productList;
  private List<Product> filteredList = new ArrayList<>();
  private Product selectedProduct;

  @PostConstruct
  public void init() {
    Product productCar = new Product();
    productCar.setId("xy123");
    productCar.setLabel("Car");
    productCar.setPrice(12399);
    productCar.setInStock(false);

    Product productBike = new Product();
    productBike.setId("xz345");
    productBike.setLabel("Bike");
    productBike.setPrice(899);
    productBike.setInStock(true);

    Product productTV = new Product();
    productTV.setId("yz678");
    productTV.setLabel("TV");
    productTV.setPrice(549);
    productTV.setInStock(true);

    this.productList = new ArrayList<>();
    this.productList.add(productCar);
    this.productList.add(productBike);
    this.productList.add(productTV);
  }

  public void remove(Product product) {
    System.out.println("Product to remove: " + product.getLabel());
    this.productList.remove(product);
    this.filteredList.remove(product);
  }

  public void removeWorkaround(Product product) {
    System.out.println("Product to remove: " + product.getLabel());
    this.productList.remove(product);
//    this.filteredList.remove(product);
    DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:dt-products");
    FilterFeature filterFeature = (FilterFeature) dataTable.getFeature(DataTableFeatureKey.FILTER);
    filterFeature.filter(FacesContext.getCurrentInstance(), dataTable);
    SortFeature sortFeature = (SortFeature) dataTable.getFeature(DataTableFeatureKey.SORT);
    sortFeature.sort(FacesContext.getCurrentInstance(), dataTable);
  }

  public List<Product> getProductList() {
    return productList;
  }

  public void setProductList(List<Product> productList) {
    this.productList = productList;
  }

  public List<Product> getFilteredList() {
    return filteredList;
  }

  public void setFilteredList(List<Product> filteredList) {
    this.filteredList = filteredList;
  }
}
