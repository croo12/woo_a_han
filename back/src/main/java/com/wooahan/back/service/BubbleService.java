package com.wooahan.back.service;

import com.wooahan.back.dto.SimpleWordInfo;
import com.wooahan.back.dto.BubbleResDto;
import com.wooahan.back.entity.Word;
import com.wooahan.back.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BubbleService {
    private final WordRepository wordRepository;

    //TODO 살찐 코드
    public List<BubbleResDto> bubbleStart(int difficulty){
        List<SimpleWordInfo> simpleWordInfoList = wordRepository.findByRandom(difficulty,20)
                .orElseThrow(()->new NoSuchElementException("bubbleStart에서 일어난 일"))
                .stream()
                .map(word -> new SimpleWordInfo(word.getName(),word.getImgUrl()))
                .collect(Collectors.toList());
        List<BubbleResDto>bubbleResDtoList = new ArrayList<>();
        int i=0;
        while(i<20) {
            List<SimpleWordInfo>tmp = simpleWordInfoList.subList(i,i+4);
            Collections.shuffle(tmp);

            BubbleResDto bubbleResDto = BubbleResDto.builder()
                    .answer(simpleWordInfoList.get(i).getName())
                    .answerImg(simpleWordInfoList.get(i).getImgUrl())
                    .cards(tmp)
                    .build();
            bubbleResDtoList.add(bubbleResDto);
            i+=4;
        }
        return bubbleResDtoList;
    }

}