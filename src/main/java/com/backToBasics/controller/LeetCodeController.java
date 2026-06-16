package com.backToBasics.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

record DemoPostRequest(
        String RN,
        String contentBody
) {}

@RestController
@RequestMapping("/api/v1/evoDemo")
public class LeetCodeController {

    private static final Logger log =
            LoggerFactory.getLogger(LeetCodeController.class);

    private final RestTemplate restTemplate;

    public LeetCodeController(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }
    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 }

    @PostMapping("/demoRestEndpoint")
    public String postDemoRestEnpoint(@RequestBody DemoPostRequest request){
        log.info("Received POST /api/v1/evoDemo with RN={} and " +
                "contentBody={}", request.RN(), request.contentBody());

        ResponseEntity<String> response = restTemplate.getForEntity(
                "https://www.evolutionfunding.com/public/", String.class
        );
        log.info("External GET Request made to https://www.evolutionfunding" +
                ".com | StatusCode={} | ContentType of response={}",
                response.getStatusCode(), response.getHeaders().getContentType());
        return "thats it. demo over.";
    }

    @GetMapping("/one")
    public int[] getSolutionOne(){
        int[] nums = new int[]{2,7, 11,15};

        int target = 9;

        int[] result = new int[]{};
        for (int num : nums) {
            for (int i : nums) {
                if (num + i == target) {
                    result = new int[]{num, i};
                }
            }
        }
        return result;
    }

    @GetMapping("/two")
    public int[] getSolutionTwo(){
        /// setting up the grounds
        ListNode node1 = new ListNode(2);
        ListNode node2 = new ListNode(4, node1);
        ListNode node3 = new ListNode(3, node2);
        ListNode node4 = new ListNode(5);
        ListNode node5 = new ListNode(6, node4);
        ListNode node6 = new ListNode(4, node5);
        ListNode l1 = node3;
        ListNode l2 = node6;

        // iterate through both linked lists and add to new linked list nodes
        int carryTheZero = 1;
        ListNode newNode = new ListNode();
        while(l1!=null || l2!=null || carryTheZero==1){
            int val1 = (l1 !=null) ? l1.val : 0;
            int val2 = (l2 !=null) ? l2.val : 0;

            int sum = val1 + val2 + carryTheZero;
            carryTheZero = sum / 10;
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }

        }
        return new int[]{0};
    }

    @GetMapping("/three")
    public int getSolutionThree(){
        String s = "abcabcbb";

        Set<Character> currentChars = new HashSet<>();
        int left = 0;
        int maxLength = 0;

        for (int right = 0; right < s.length(); right++){
            while (currentChars.contains(s.charAt(right))) {
                currentChars.remove(s.charAt(left));
                left++;
            }
            currentChars.add(s.charAt(right));
            maxLength = Math.max(maxLength, right - left + 1);
        }
        return maxLength;
    }


}
