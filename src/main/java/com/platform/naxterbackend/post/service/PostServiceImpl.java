package com.platform.naxterbackend.post.service;

import com.platform.naxterbackend.post.model.Post;
import com.platform.naxterbackend.post.model.Tag;
import com.platform.naxterbackend.post.model.UserPost;
import com.platform.naxterbackend.post.repository.PostRepository;
import com.platform.naxterbackend.post.repository.TagRepository;
import com.platform.naxterbackend.post.validator.SearchValidator;
import com.platform.naxterbackend.profile.model.Profile;
import com.platform.naxterbackend.profile.repository.ProfileRepository;
import com.platform.naxterbackend.theme.model.Theme;
import com.platform.naxterbackend.theme.repository.ThemeRepository;
import com.platform.naxterbackend.user.model.User;
import com.platform.naxterbackend.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private ThemeRepository themeRepository;
    @Autowired
    private UserRepository userRepository;


    @Override
    public Post getPost(String id) {
        return this.postRepository.findById(id).get();
    }

    @Override
    public List<Post> getPosts() {
        return this.postRepository.findAll();
    }

    @Override
    public List<Post> getTopPosts() {
        return this.postRepository.findTop10ByOrderByRatingDesc();
    }

    @Override
    public List<Post> getTopPostsByAuthor(String name) {
        User user = this.userRepository.findByNameIgnoreCase(name);

        return this.postRepository.findTop4ByUserOrderByRatingDesc(user);
    }

    @Override
    public List<Post> searchPosts(String name, String theme, String user) {
        Sort sort = Sort.by(Sort.Direction.DESC, "rating");
        if(SearchValidator.validParam(name) && !SearchValidator.validParam(theme) && !SearchValidator.validParam(user)) {
            return this.postRepository.findAllByName(name, sort);
        } else if(SearchValidator.validParam(name) && SearchValidator.validParam(theme) && !SearchValidator.validParam(user)) {
            return this.postRepository.findAllByNameAndTheme(name, theme, sort);
        } else if(SearchValidator.validParam(name) && !SearchValidator.validParam(theme) && SearchValidator.validParam(user)) {
            return this.postRepository.findAllByNameAndUser(name, user, sort);
        } else if(!SearchValidator.validParam(name) && SearchValidator.validParam(theme) && !SearchValidator.validParam(user)) {
            return this.postRepository.findAllByTheme(theme, sort);
        } else if(!SearchValidator.validParam(name) && SearchValidator.validParam(theme) && SearchValidator.validParam(user)) {
            return this.postRepository.findAllByThemeAndUser(theme, user, sort);
        } else {
            return this.postRepository.findAllByNameAndThemeAndUser(name, theme, user, sort);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public Post createPost(UserPost userPost) {
        Post post = new Post();
        post.setName(userPost.getName());
        post.setDescription(userPost.getDescription());

        User user = this.userRepository.findByNameIgnoreCase(userPost.getUser());
        post.setUser(user);

        Theme theme = this.themeRepository.findByNameIgnoreCase(userPost.getTheme());
        post.setTheme(theme);

        post.setDate(new Date());

        List<Tag> tags = new ArrayList<>();
        post.setTags(tags);

        return this.postRepository.save(post);
    }

    @Override
    @Transactional(readOnly = false)
    public Post editPost(String id, UserPost userPost) {
        Post post = this.postRepository.findById(id).get();
        post.setName(userPost.getName());
        post.setDescription(userPost.getDescription());

        return this.postRepository.save(post);
    }

    @Override
    @Transactional(readOnly = false)
    public String delete(String id) {
        Post post = this.postRepository.findById(id).get();

        for(Tag tag : post.getTags()) {
            this.tagRepository.delete(tag);
        }

        this.postRepository.delete(post);

        return id;
    }

    @Override
    @Transactional(readOnly = false)
    public Post rate(String id, Integer newRating) {
        Post postSaved = this.calculateRatingPost(id, newRating);
        this.calculateRatingProfile(postSaved);

        return postSaved;
    }

    private Post calculateRatingPost(String id, Integer newRating) {
        Post post = this.postRepository.findById(id).get();

        BigInteger numRatings = post.getNumRatings();
        BigDecimal avgRating = post.getRating();

        BigDecimal ratings = new BigDecimal(numRatings).multiply(avgRating);
        BigDecimal newRatings = ratings.add(new BigDecimal(newRating));

        BigInteger newNumRatings = numRatings.add(BigInteger.ONE);
        BigDecimal newAvgRating = newRatings.divide(new BigDecimal(newNumRatings), 2, RoundingMode.HALF_UP);

        post.setNumRatings(newNumRatings);
        post.setRating(newAvgRating.setScale(2, RoundingMode.HALF_UP));

        return this.postRepository.save(post);
    }

    private void calculateRatingProfile(Post post) {
        User user = post.getUser();

        Long numPosts = this.postRepository.countByUser(user);
        List<Post> posts = this.postRepository.findByUser(user);

        List<BigDecimal> listRatings = posts.stream().map(p -> p.getRating()).collect(Collectors.toList());
        BigDecimal totalRatings = listRatings.stream().reduce((total, rating)-> total.add(rating)).get();
        BigDecimal avgRatings = totalRatings.divide(new BigDecimal(numPosts), 2, RoundingMode.HALF_UP);

        user.setRating(avgRatings);

        this.userRepository.save(user);
    }
}
