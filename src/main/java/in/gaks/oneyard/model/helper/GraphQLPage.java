package in.gaks.oneyard.model.helper;

import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/2 下午1:59
 */
@NoArgsConstructor
@AllArgsConstructor
public class GraphQLPage<T> {

  @GraphQLNonNull
  @GraphQLQuery(name = "content", description = "The content of the page")
  public List<T> content;

  @GraphQLNonNull
  @GraphQLQuery(name = "last", description = "Whether or not this page is the last page of the collection")
  public boolean last;

  @GraphQLNonNull
  @GraphQLQuery(name = "totalElements", description = "The total number of elements that exist in the collection")
  public long totalElements;

  @GraphQLNonNull
  @GraphQLQuery(name = "totalPages", description = "The total number of pages that the collection has")
  public int totalPages;

  @GraphQLNonNull
  @GraphQLQuery(name = "size", description = "The max number of items in the content of this page")
  public int size;

  @GraphQLNonNull
  @GraphQLQuery(name = "number", description = "The which page of totalPages this is")
  public int number;

  @GraphQLNonNull
  @GraphQLQuery(name = "first", description = "Whether or not this page is the first page in the collection")
  public boolean first;

  @GraphQLNonNull
  @GraphQLQuery(name = "numberOfElements", description = "The actual number of content elements in this page")
  public int numberOfElements;


  public static <T> GraphQLPage from(Page<T> page) {
    return new GraphQLPage(
        page.getContent(),
        page.isLast(),
        page.getTotalElements(),
        page.getTotalPages(),
        page.getSize(),
        page.getNumber(),
        page.isFirst(),
        page.getNumberOfElements()
    );
  }
}
