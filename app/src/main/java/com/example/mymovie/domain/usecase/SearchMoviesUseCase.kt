import androidx.paging.PagingData
import com.example.mymovie.domain.model.Movie
import com.example.mymovie.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(query: String): Flow<PagingData<Movie>> {
        return repository.searchMoviesPaging(query)
    }
}