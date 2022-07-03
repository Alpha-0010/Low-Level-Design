import java.util.List;
import java.util.stream.Stream;

enum Color {
    RED,GREEN,BLUE
}

enum Size {
    SMALL, MEDIUM, LARGE
}

class Product {
    public String name;
    public Color color;
    public Size size;

    public Product (String name, Color color, Size size) {
        this.name = name;
        this.color = color;
        this.size = size;
    }
}

/* Let's say initially we were only required to filter by only color and after sometime we are asked to 
filter size also. In this case we will have to make modifications in the ProductFilter Class add 
more methods for more filtering. This violated the Open Closed Principle which means open for 
entension but closed for modification.
*/
class ProductFilter {
    
    public Stream<Product> filterByColor(List<Product> products,Color color) {
        return products.stream().filter( p -> p.color == color);
    }

    public Stream<Product> filterBySize(List<Product> products,Size size) {
        return products.stream().filter( p -> p.size == size);
    }

}

/* 
So instead of this we can use interfaces and make it more generic like the filter specs can be extended
further.
*/
interface Specification<T> {
    boolean isSatisfied(T item);
}

interface Filter<T> {
    Stream<T> filter(List<T> item,Specification<T> spec);
}

class ColorSpecification implements Specification<Product> {
    Color color;
    ColorSpecification(Color color) {
        this.color = color;
    }
    @Override
    public boolean isSatisfied(Product item) {
        return color == item.color;
    }
}

class SizeSpecification implements Specification<Product> {
    Size size;
    SizeSpecification(Size size) {
        this.size = size;
    }
    @Override
    public boolean isSatisfied(Product item) {
        return size == item.size;
    }
}

class BetterFilter implements Filter<Product> {
    @Override
    public Stream<Product> filter(List<Product> items,Specification<Product> spec) {
        return items.stream().filter( p -> spec.isSatisfied(p));
    }
}

class AndSpecification<T> implements Specification<T> {
    private Specification<T> first, second;
    AndSpecification(Specification<T> first,Specification<T> second) {
        this.first = first;
        this.second = second;
    }
    @Override
    public boolean isSatisfied(T item) {
        return first.isSatisfied(item) && second.isSatisfied(item);
    }
}


public class OpenClosedPrinciple {
    public static void main(String[] args) {
        Product apple = new Product("Apple",Color.RED,Size.SMALL);
        Product tree = new Product("Tree",Color.GREEN,Size.LARGE);
        Product house = new Product("House",Color.BLUE,Size.MEDIUM);

        // Not using the OpenClosedPrinciple...
        List<Product> products = List.of(apple,tree,house);
        ProductFilter pf = new ProductFilter();
        System.out.println("Filter all the products with color Green");
        pf.filterByColor(products, Color.GREEN)
            .forEach( p -> System.out.println(p.name+" is Green"));

        // Using the OpenClosedPrinciple...
        BetterFilter bf = new BetterFilter();
        System.out.println("Filter all the products with color Green using OCP");
        bf.filter(products,new ColorSpecification(Color.GREEN))
            .forEach( p -> System.out.println(p.name+" is Green"));
        // Both color and size satisfies using the OpenClosedPrinciple...
        System.out.println("Filter all the products with color Green and Size Large using OCP");
        bf.filter(products,
        new AndSpecification<>(
          new ColorSpecification(Color.GREEN),
          new SizeSpecification(Size.LARGE)
        ))
        .forEach(p -> System.out.println( p.name + " is large and green"));
    }
}
