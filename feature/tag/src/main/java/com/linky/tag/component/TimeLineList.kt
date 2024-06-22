package com.linky.tag.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.ImageLoader
import coil.compose.SubcomposeAsyncImage
import com.linky.design_system.R
import com.linky.design_system.ui.component.more.TimeLineTagChip
import com.linky.design_system.ui.component.text.LinkyText
import com.linky.design_system.ui.theme.ColorFamilyGray600AndGray400
import com.linky.design_system.ui.theme.ColorFamilyGray800AndGray300
import com.linky.design_system.ui.theme.ColorFamilyGray900AndGray100
import com.linky.design_system.ui.theme.ColorFamilyWhiteAndGray999
import com.linky.design_system.ui.theme.Gray400
import com.linky.design_system.util.clickableRipple
import com.linky.model.Link

@Composable
internal fun TimeLineList(
    modifier: Modifier = Modifier,
    state: LazyListState,
    imageLoader: ImageLoader,
    links: LazyPagingItems<Link>,
    onClick: (Link) -> Unit,
    onCopyLink: (Link) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        state = state,
        contentPadding = PaddingValues(16.dp)
    ) {
        items(
            count = links.itemCount,
            key = links.itemKey { it.id ?: 0L },
            contentType = links.itemContentType { "TimeLineItems" }
        ) { index ->
            links[index]?.let { link ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                        .clickableRipple(enableRipple = false) { onClick.invoke(link) },
                    shape = RoundedCornerShape(12.dp),
                    backgroundColor = ColorFamilyWhiteAndGray999
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, bottom = 12.dp, start = 12.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                LinkyText(
                                    text = link.createAtFormat,
                                    color = ColorFamilyGray800AndGray300,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Spacer(
                                    modifier = Modifier
                                        .padding(horizontal = 4.dp)
                                        .width(1.dp)
                                        .height(8.dp)
                                        .background(ColorFamilyGray600AndGray400)
                                )
                                LinkyText(
                                    text = link.readCountFormat,
                                    color = ColorFamilyGray800AndGray300,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            SubcomposeAsyncImage(
                                modifier = Modifier
                                    .size(98.dp)
                                    .clip(RoundedCornerShape(4.dp)),
                                imageLoader = imageLoader,
                                model = link.openGraphData.image,
                                contentScale = ContentScale.Crop,
                                contentDescription = "thumbnail"
                            )
                            Column(
                                modifier = Modifier
                                    .height(98.dp)
                                    .padding(horizontal = 10.dp),
                            ) {
                                if (link.memo.isNotEmpty()) {
                                    LinkyText(
                                        text = link.memo,
                                        color = ColorFamilyGray900AndGray100,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        lineHeight = 20.sp
                                    )
                                    Spacer(modifier = Modifier.padding(bottom = 7.dp))
                                }
                                LazyRow(
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    items(link.tags) { tag ->
                                        TimeLineTagChip(
                                            modifier = Modifier.padding(end = 3.dp),
                                            tagName = tag.name,
                                            onClick = {
                                                Log.d("123123", "link: $link")
                                                Log.d("123123", "tag: $tag")
                                            }
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.weight(1f))
                                // 태그
                                Spacer(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(1.dp)
                                        .background(Gray400)
                                )
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    LinkyText(
                                        text = link.openGraphData.title ?: "",
                                        color = ColorFamilyGray800AndGray300,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.weight(0.7f),
                                    )
                                    Spacer(modifier = Modifier.weight(0.2f))

                                    Image(
                                        painter = painterResource(R.drawable.ico_tag_copy),
                                        contentDescription = "copy",
                                        modifier = Modifier
                                            .weight(0.1f)
                                            .clickableRipple(radius = 10.dp) {
                                                onCopyLink.invoke(link)
                                            },
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        if (links.loadState.append is LoadState.Loading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}