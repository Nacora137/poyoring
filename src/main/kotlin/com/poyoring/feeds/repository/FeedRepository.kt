package com.poyoring.feeds.repository

import com.poyoring.feeds.entity.Feed
import org.springframework.data.jpa.repository.JpaRepository

interface FeedRepository : JpaRepository<Feed, Long>