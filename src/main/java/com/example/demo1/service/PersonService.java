package com.example.demo1.service;

import com.example.demo1.domain.Block;
import com.example.demo1.domain.Person;
import com.example.demo1.repository.BlockRepository;
import com.example.demo1.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private BlockRepository blockRepository;

    //차단된 사람 외에 전체 사람을 가져오는 로직
    public List<Person> getPeopleExcludeBlocks(){
        List<Person> people = personRepository.findAll();
        List<Block> blocks = blockRepository.findAll();

        //peaple 에서 blocks를 빼고싶은데 객체가 서로 다르기때문에 바로 뺼 수는 없음
        //블럭에서 가지고 있는 네임만 가져옴
        //blocks안에 있는 Block을 순회하면서 getName을 가져옴
        List<String> blockNames = blocks.stream().map(Block::getName).collect(Collectors.toList());
        //map의 리턴값이 스트림이기 때문에 collect로 List로 변경해줌

        return people.stream().filter(person -> !blockNames.contains(person.getName())).collect(Collectors.toList());
    }
}
