package com.kitsune.backend;

import com.kitsune.backend.youtube.YouTubeService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor
public class YouTubeTest {
    @Spy
    @InjectMocks
    YouTubeService youTubeService;


    @Test
    public void test_parse_url() {

        assertThat(youTubeService.parseVideoId("https://www.youtube.com/watch?v=1"))
                .isEqualTo("1");


        assertThat(youTubeService.parseVideoId("https://youtu.be/2"))
                .isEqualTo("2");


        assertThat(youTubeService.parseVideoId("https://www.youtube.com/live/3"))
                .isEqualTo("3");


        assertThat(youTubeService.parseVideoId("https://holodex.net/watch/4"))
                .isEqualTo("4");

        assertThat(youTubeService.parseVideoId("5"))
                .isEqualTo("5");
    }


}
